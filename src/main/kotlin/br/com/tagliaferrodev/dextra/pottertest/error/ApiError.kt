package br.com.tagliaferrodev.dextra.pottertest.error

import org.springframework.http.HttpStatus
import java.time.Instant

data class ApiError(
        val status: HttpStatus,
        var message: String?,
        val timestamp: Long = Instant.now().toEpochMilli(),
        val debugMessage: String? = null,
        val subErrors: List<ApiSubError> = listOf()) {

    init {
        message = message ?: "Something went wrong."
    }

    data class ApiSubError(val attribute: String? = null,
                           val message: String,
                           val field: String? = null,
                           val rejectedValue: Any? = null,
                           val stack: String? = null)
}