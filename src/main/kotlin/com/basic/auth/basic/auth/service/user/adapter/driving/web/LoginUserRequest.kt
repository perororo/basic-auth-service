package com.basic.auth.basic.auth.service.user.adapter.driving.web

import com.basic.auth.basic.auth.service.user.port.driving.web.UserLoginCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank


class LoginUserRequest(
    @field:Email
    @field:NotBlank
    val email: String,
    @field:Min(8)
    val password: String
) {
    fun toCommand() =  UserLoginCommand(
        email = email,
        password = password
    )
}
