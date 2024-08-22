package com.basic.auth.basic.auth.service.user.adapter.driving.web

import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val companyName: String,
    val roleInCompany: String,
    val industry: String,
    val registrationState: RegistrationState,
    val comment: String,
)

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        email = email,
        companyName = companyName,
		roleInCompany = roleInCompany,
		industry = industry,
		registrationState = registrationState,
		comment = comment,
    )
}
