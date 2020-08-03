package br.com.tagliaferrodev.dextra.pottertest.util

import br.com.tagliaferrodev.dextra.pottertest.config.toJson
import br.com.tagliaferrodev.dextra.pottertest.error.ApiError
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletResponse

object SimpleResponseSender {
    fun send(response: HttpServletResponse?,
             error: ApiError) {
        response?.status = error.status.value()
        response?.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
        response?.writer?.append(error.toJson(JsonInclude.Include.NON_NULL))
    }

    fun send(response: HttpServletResponse?,
             status: HttpStatus,
             responseObject: Any) {
        response?.status = status.value()
        response?.addHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
        response?.writer?.append(responseObject.toJson())
    }
}