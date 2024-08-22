package com.basic.auth.basic.auth.service.user.adapter.driving.web

import com.basic.auth.basic.auth.service.common.util.LoggingContext
import com.basic.auth.basic.auth.service.user.model.UserId
import com.basic.auth.basic.auth.service.user.port.driving.web.UserWebFacade
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("api/users")
class UserController(private val userWebFacade: UserWebFacade) {
    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<out UserResponse> {
        LoggingContext.putUseCase(this::findById)
        return userWebFacade.findById(UUID.fromString(id))
            ?.toResponse()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        LoggingContext.putUseCase(this::getAllUsers)
        return userWebFacade.getAllUsers()
            .map { it.toResponse() }
            .let { ResponseEntity.ok(it) }
    }
}
