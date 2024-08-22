package com.basic.auth.basic.auth.service.user.adapter.driving.web

import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.port.driving.web.UserRegistrationCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterUserRequest(
    @field:NotBlank
    var firstName: String?,
    @field:NotBlank
    var lastName: String?,

    @field:Email
    var email: String?,

    @field:Size(min = 8)
    var password: String?,

    @field:NotBlank
    var companyName: String,
    var roleInCompany: String,
    var industry: String,
    var comment: String

) {
    fun toDomain() = UserRegistrationCommand(
        firstName = firstName!!,
        lastName = lastName!!,
        email = email!!,
        password = password!!,
        companyName = companyName,
        roleInCompany = roleInCompany,
        industry = industry,
        registrationState = RegistrationState.REQUESTED,
        comment = comment
    )

}