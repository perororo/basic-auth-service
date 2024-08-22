package com.basic.auth.basic.auth.service.user.adapter.security.config

import com.basic.auth.basic.auth.service.user.adapter.security.CustomUserDetailsService
import com.basic.auth.basic.auth.service.user.adapter.security.JwtAuthenticationFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val passwordEncoder: PasswordEncoder,
) {


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests { matcherRegistry ->
                matcherRegistry.requestMatchers("/actuator/health").permitAll()
                matcherRegistry.requestMatchers("/api/auth/**").permitAll()
                matcherRegistry.requestMatchers("/error").permitAll()
                matcherRegistry.requestMatchers("/admin/**").hasRole("ADMIN")
                matcherRegistry.anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
//            .httpBasic(Customizer.withDefaults())
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .csrf { it.disable() }
            .authenticationProvider(authenticationProvider(customUserDetailsService))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationProvider(customUserDetailsService: CustomUserDetailsService): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(customUserDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return authenticationProvider
    }

}