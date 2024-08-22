package com.basic.auth.basic.auth.service.user.port.driving.web

import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import java.util.*

interface UserWebFacade {
    fun findById(id: UUID): User?
    fun getAllUsers(): Set<User>
    fun findByEmail(email: String): User?
}

data class UserRegistrationCommand(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val companyName: String,
    val roleInCompany: String,
    var industry: String,
    var registrationState: RegistrationState,
    var comment: String
)
