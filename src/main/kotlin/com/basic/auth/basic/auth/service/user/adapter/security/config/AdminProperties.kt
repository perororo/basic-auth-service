package com.basic.auth.basic.auth.service.user.adapter.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "basic.auth")
data class AdminProperties(
    var email: String = "",
    var password: String = ""
)