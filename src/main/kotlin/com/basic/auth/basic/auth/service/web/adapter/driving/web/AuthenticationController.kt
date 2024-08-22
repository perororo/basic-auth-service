package com.basic.auth.basic.auth.service.web.adapter.driving.web

import com.basic.auth.basic.auth.service.common.util.LoggingContext
import com.basic.auth.basic.auth.service.user.adapter.driving.web.*
import com.basic.auth.basic.auth.service.user.adapter.security.AuthenticatedUser
import com.basic.auth.basic.auth.service.user.port.driving.web.AuthenticationWebFacade
import com.basic.auth.basic.auth.service.user.port.driving.web.JwtWebFacade
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthenticationController(
    private val authenticationWebFacade: AuthenticationWebFacade,
    private val jwtWebFacade: JwtWebFacade,
) {

    @PostMapping
    fun registerUser(@Valid @RequestBody registerUserRequest: RegisterUserRequest): ResponseEntity<UserResponse> {
        LoggingContext.putUseCase(this::registerUser)
        val user = registerUserRequest.toDomain()
        val registeredUser = authenticationWebFacade.createUser(user)
        return ResponseEntity(registeredUser?.toResponse(), HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginUserRequest: LoginUserRequest?): ResponseEntity<LoginResponse> {
        val authenticatedUser = authenticationWebFacade.authenticate(loginUserRequest!!.toCommand())

        val jwtToken = jwtWebFacade.generateToken(AuthenticatedUser(user = authenticatedUser!!))

        val loginResponse= LoginResponse(
            token = jwtToken,
            expiresIn = jwtWebFacade.getExpirationTime()
        )

        return ResponseEntity.ok(loginResponse)
    }

}
