package br.com.tagliaferrodev.dextra.pottertest.error

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    private fun sendResponse(apiError: ApiError): ResponseEntity<ApiError> {
        return ResponseEntity(apiError, apiError.status)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(ex: EntityNotFoundException): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.NOT_FOUND, message = ex.message)
        return sendResponse(error)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.BAD_REQUEST, message = ex.message)
        return sendResponse(error)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ApiError> {
        val subErrors = ex.constraintViolations.map {
            ApiError.ApiSubError(
                    field = it.propertyPath.toString(),
                    rejectedValue = it.invalidValue.toString(),
                    message = it.message
            )
        }
        val error = ApiError(status = HttpStatus.BAD_REQUEST, message = "Ocorreu um erro na validação dos dados", subErrors = subErrors)

        return sendResponse(error)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.BAD_REQUEST, message = "Houve algum erro na camada de dados", debugMessage = ex.cause?.cause?.message
                ?: ex.message)
        return sendResponse(error)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.UNAUTHORIZED, message = "Você não tem acesso para realizar esta operação", debugMessage = ex.cause?.cause?.message
                ?: ex.message)
        return sendResponse(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiError> {
        val error = ApiError(status = HttpStatus.BAD_REQUEST, message = "Erro não mapeado", debugMessage = ex.message)
        return sendResponse(error)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        return sendResponse(ApiError(HttpStatus.BAD_REQUEST, message = "Possível erro no valor de algum atributo e/ou faltando atributos", debugMessage = ex.cause?.message)) as ResponseEntity<Any>
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val subErrors = ex.bindingResult.allErrors.mapNotNull {
            if (it is FieldError) {
                ApiError.ApiSubError(
                        attribute = it.objectName,
                        field = it.field,
                        rejectedValue = it.rejectedValue,
                        message = it.defaultMessage.toString()
                )
            } else {
                null
            }
        }
        return sendResponse(ApiError(HttpStatus.BAD_REQUEST, message = "Ocorreu um erro na validação dos dados", subErrors = subErrors)) as ResponseEntity<Any>
    }

    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ApiError(HttpStatus.METHOD_NOT_ALLOWED, message = "Este método não é suportado para esta URL. Os métodos aceitos são: ${ex.supportedMethods?.joinToString()}")

        return sendResponse(error) as ResponseEntity<Any>
    }
}
