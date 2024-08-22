package com.basic.auth.basic.auth.service.user.port.driving.web

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails

interface JwtWebFacade {
    fun extractUsername(token: String): String
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T
    fun generateToken(userDetails: UserDetails): String
    fun getExpirationTime(): Long
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean

}