package com.basic.auth.basic.auth.service.user.adapter.driven.persistence.model

import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "APPLICATION_USER")
data class UserEntity(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val companyName: String,
    val roleInCompany: String,
    val industry: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "REGISTRATION_STATE")
    val registrationState: RegistrationState,
    val comment: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "USER_ROLES",
        joinColumns = [JoinColumn(name = "user_id", columnDefinition = "UUID")]
    )
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<Role>
) {

    fun toDomain() = User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = password,
        companyName = companyName,
        roleInCompany = roleInCompany,
        industry = industry,
        registrationState = registrationState,
        comment = comment,
        roles = roles
    )
}