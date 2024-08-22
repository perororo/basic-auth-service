package com.basic.auth.basic.auth.service.user.adapter.driven.persistence

import com.basic.auth.basic.auth.service.common.util.toNullable
import com.basic.auth.basic.auth.service.user.adapter.driven.persistence.model.UserEntity
import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.adapter.security.config.AdminProperties
import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import com.basic.auth.basic.auth.service.user.port.driven.persistence.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JpaUserRepository(
    private val jpaUserRepository: JpaUserEntityRepository,
    private val adminProperties: AdminProperties
) : UserRepository {
    @PostConstruct
    fun createAdminUser(
    ) {
        if (findByEmail(adminProperties.email) == null) {

            val admin = User(
                id = null,
                firstName = "admin",
                lastName = "admin",
                email = adminProperties.email,
                password = adminProperties.password,
                companyName = "admin",
                roleInCompany = "boss",
                industry = "bananas",
                registrationState = RegistrationState.CONFIRMED,
                comment = "",
                roles = mutableSetOf(Role.ADMIN)
            )
            createOrUpdate(admin)
        }
    }
    override fun findById(id: UUID): User? {
        return jpaUserRepository.findById(id).toNullable()?.toDomain()
    }

    override fun findAll(): Set<User> {
        return jpaUserRepository.findAll().map { it.toDomain() }.toSet()
    }

    override fun createOrUpdate(user: User): User {
        return jpaUserRepository.save(user.toEntity()).toDomain()
    }

    override fun findByEmail(email: String): User? {
        return jpaUserRepository.findByEmail(email)?.toDomain()
    }
}

interface JpaUserEntityRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
}

fun User.toEntity() = UserEntity(
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
