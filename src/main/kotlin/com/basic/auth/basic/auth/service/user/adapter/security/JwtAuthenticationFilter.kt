package com.basic.auth.basic.auth.service.user.adapter.security

import com.basic.auth.basic.auth.service.user.adapter.security.config.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            logger.debug("No JWT token found in request")
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        try {
            val userEmail = jwtService.extractUsername(jwt)
            logger.debug("Extracted user email from JWT: $userEmail")

            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null) {
                authenticateUser(userEmail, jwt, request)
            } else {
                logger.debug("User already authenticated: ${authentication.name}")
            }

            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            handleException(response, exception)
        }
    }

    private fun authenticateUser(userEmail: String, jwt: String, request: HttpServletRequest) {
        try {
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            logger.debug("Loaded user details: $userDetails")

            if (jwtService.isTokenValid(jwt, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                ).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }
                SecurityContextHolder.getContext().authentication = authToken
                logger.debug("Authentication set in SecurityContextHolder for user: $userEmail")
            } else {
                logger.warn("Invalid JWT token for user: $userEmail")
            }
        } catch (exception: Exception) {
            logger.error("Failed to process JWT token for user: $userEmail", exception)
            throw ServletException("Invalid or expired JWT token", exception)
        }
    }


    private fun handleException(response: HttpServletResponse, exception: Exception) {
        logger.error("Exception during filtering: ${exception.message}", exception)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token")
    }
}
