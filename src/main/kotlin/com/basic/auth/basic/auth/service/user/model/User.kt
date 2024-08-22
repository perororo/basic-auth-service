package com.basic.auth.basic.auth.service.user.model

import com.basic.auth.basic.auth.service.user.adapter.security.Role
import java.util.UUID

data class User(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val companyName: String,
    val roleInCompany: String,
    val industry: String,
    val registrationState: RegistrationState,
    val comment: String,
    val roles: MutableSet<Role>
)