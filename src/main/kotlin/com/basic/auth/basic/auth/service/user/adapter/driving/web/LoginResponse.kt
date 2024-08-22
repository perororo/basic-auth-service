package com.basic.auth.basic.auth.service.user.adapter.driving.web

data class LoginResponse(
    val token: String,
    val expiresIn: Long,
)
