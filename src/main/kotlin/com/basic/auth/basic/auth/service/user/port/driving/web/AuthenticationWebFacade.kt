package com.basic.auth.basic.auth.service.user.port.driving.web

import com.basic.auth.basic.auth.service.user.model.User

interface AuthenticationWebFacade {
    fun authenticate(userLoginCommand: UserLoginCommand): User?
    fun createUser(userRegistrationCommand: UserRegistrationCommand): User?
}

data class UserLoginCommand(
    val email: String,
    val password: String,
)