package com.basic.auth.basic.auth.service.user.adapter.security

import com.basic.auth.basic.auth.service.user.model.User
import com.basic.auth.basic.auth.service.user.port.driven.persistence.UserRepository
import com.basic.auth.basic.auth.service.user.port.driving.web.AuthenticationWebFacade
import com.basic.auth.basic.auth.service.user.port.driving.web.UserLoginCommand
import com.basic.auth.basic.auth.service.user.port.driving.web.UserRegistrationCommand
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
) : AuthenticationWebFacade {

    override fun createUser(userRegistrationCommand: UserRegistrationCommand): User {
        val user = User(
            id = null,
            email = userRegistrationCommand.email,
            firstName = userRegistrationCommand.firstName,
            lastName = userRegistrationCommand.lastName,
            password = userRegistrationCommand.password,
            companyName = userRegistrationCommand.companyName,
            roleInCompany = userRegistrationCommand.roleInCompany,
            industry = userRegistrationCommand.industry,
            registrationState = userRegistrationCommand.registrationState,
            comment = userRegistrationCommand.comment,
            roles = mutableSetOf(Role.END_USER)
        )
        return userRepository.createOrUpdate(user)
    }

    override fun authenticate(userLoginCommand: UserLoginCommand): User? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userLoginCommand.email,
                userLoginCommand.password
            )
        )

        return userRepository.findByEmail(userLoginCommand.email)
    }
}