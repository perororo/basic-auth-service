package com.basic.auth.basic.auth.service.adapter.driven.persistence

import com.basic.auth.basic.auth.service.common.driven.persistence.AbstractRepositoryTest
import com.basic.auth.basic.auth.service.user.adapter.driven.persistence.JpaUserEntityRepository
import com.basic.auth.basic.auth.service.user.adapter.driven.persistence.JpaUserRepository
import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.adapter.security.config.AdminProperties
import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import java.util.*

@ContextConfiguration(classes = [JpaUserRepository::class, JpaUserEntityRepository::class, AdminProperties::class])
@EnableJpaRepositories(basePackages = ["com.basic.auth.basic.auth.service.user.adapter.driven.persistence"])
@EntityScan(basePackages = ["com.basic.auth.basic.auth.service.user.adapter.driven.persistence"])
class JpaUserRepositoryTest : AbstractRepositoryTest() {

    @Autowired
    private lateinit var userRepository: JpaUserRepository

    @Autowired
    private lateinit var jpaUserRepository: JpaUserEntityRepository


    @BeforeEach
    fun clear() {
        jpaUserRepository.deleteAll()
        flushAndClear()
    }

    private final val user = User(
        id = null,
        firstName = "Admin",
        lastName = "Admin",
        email = "test@test.com",
        password = "some-password",
        companyName = "some-company",
        roleInCompany = "some-role",
        industry = "some-industry",
        registrationState = RegistrationState.REQUESTED,
        comment = "some-comment",
        roles = mutableSetOf(Role.ADMIN)
    )

    @Nested
    inner class `findAll()` {
        @Test
        fun `SHOULD return an empty set WHEN no user is found`() {
            // GIVEN
            // WHEN
            val result = userRepository.findAll()

            // THEN
            result shouldBe emptySet()
        }

        @Test
        fun `SHOULD return a set of all users`() {
            // GIVEN
            val users = listOf(
                user,
                user
            )
            users.forEach(userRepository::createOrUpdate)

            // WHEN
            val result = userRepository.findAll()

            // THEN
            result.size shouldBe 2
        }
    }

    @Nested
    inner class `findById()` {
        @Test
        fun `SHOULD return a user WHEN a user with this id is found`() {
            // GIVEN
            val createdUser = userRepository.createOrUpdate(user)

            // WHEN
            val result = userRepository.findById(createdUser.id!!)

            // THEN
            result shouldBe user.copy(id = createdUser.id)
        }

        @Test
        fun `SHOULD return _null_ WHEN _no_ user with this id exists`() {
            // GIVEN
            userRepository.createOrUpdate(user)

            // WHEN
            val result = userRepository.findById(UUID.randomUUID())

            // THEN
            result shouldBe null
        }
    }

    @Nested
    inner class `createOrUpdate()` {
        @Test
        fun `SHOULD create a new user`() {
            // GIVEN
            // WHEN
            val result = userRepository.createOrUpdate(user)

            // THEN
            result shouldBe user.copy(id = result.id)
        }

        @Test
        fun `SHOULD update an existing user`() {
            // GIVEN
            val createdUser = userRepository.createOrUpdate(user)

            // WHEN
            val updatedUser = createdUser.copy(
                roleInCompany = "new-role",
            )
            val result = userRepository.createOrUpdate(updatedUser)

            // THEN
            result shouldBe updatedUser
        }
    }

}