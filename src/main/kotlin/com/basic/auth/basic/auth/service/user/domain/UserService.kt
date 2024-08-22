package com.basic.auth.basic.auth.service.user.domain

import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.model.User
import com.basic.auth.basic.auth.service.user.model.UserId
import com.basic.auth.basic.auth.service.user.port.driven.persistence.UserRepository
import com.basic.auth.basic.auth.service.user.port.driving.web.UserRegistrationCommand
import com.basic.auth.basic.auth.service.user.port.driving.web.UserWebFacade
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) : UserWebFacade {

    override fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }

    override fun getAllUsers(): Set<User> {
        return userRepository.findAll()
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}
