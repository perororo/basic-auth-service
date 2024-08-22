package com.basic.auth.basic.auth.service.common.adapter.driving.web

import com.basic.auth.basic.auth.service.common.exceptions.NotFoundException
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.URI

@ControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler
    fun notFound(e: NotFoundException): ResponseEntity<*> {
        return ResponseEntity.of(
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        "${e.objectType.simpleName} with ${e.id} was not found",
                ).also {
                    it.type = URI("basic-auth-service:not-found")
                },
        ).build<ProblemDetail>()
    }

    @ExceptionHandler
    fun validationException(e: BindException): ResponseEntity<*> {
        val errors = e.bindingResult.allErrors
                .map {
                    when (it) {
                        is FieldError -> ValidationError(field = it.field, error = it.defaultMessage
                                ?: "invalid", errorCode = it.code, value = it.rejectedValue)

                        else -> ValidationError(error = it.defaultMessage ?: "invalid", errorCode = it.code)
                    }
                }
                // stable sorting is important for testing
                .sortedWith(compareBy({ it.field }, { it.error }))

        return ResponseEntity.of(
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        "Validation failed",
                ).also {
                    it.type = URI("basic-auth-service:validation-failed")
                    it.setProperty("errors", errors)
                },
        ).build<ProblemDetail>()
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidationError(val error: String, val field: String? = null, val errorCode: String? = null, val value: Any? = null)