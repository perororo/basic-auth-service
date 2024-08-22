package com.basic.auth.basic.auth.service.adapter.security

import com.basic.auth.basic.auth.service.user.adapter.security.AuthenticatedUser
import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.UUID

class DomainUserDetailsTest {

    @Test
    fun `should return correct authorities`() {
        // Given
        val user = User(
            id = UUID.randomUUID(),
            firstName = "Test",
            lastName = "User",
            email = "test@example.com",
            password = "{bcrypt}$2a$10\$PLbRTV7qLT2xJgcV8TU3AeiwyEzbtIa/LDIFhG3yzlTlIzfrUs5fW",
            companyName = "TestCompany",
            roleInCompany = "employee",
            industry = "IT",
            registrationState = RegistrationState.CONFIRMED,
            comment = "",
            roles = mutableSetOf(Role.ADMIN)
        )
        val domainUserDetails = AuthenticatedUser(user)

        // When
        val authorities = domainUserDetails.authorities

        // Then
        assertTrue(authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")))
        user.roles.forEach { role ->
            assertTrue(
                authorities.contains(SimpleGrantedAuthority("ROLE_${role.name}")),
                "Authority ROLE_${role.name} is missing!"
            )
        }
    }

    @Test
    fun `should return correct user details`() {
        // Given
        val user = User(
            id = UUID.randomUUID(),
            firstName = "Test",
            lastName = "User",
            email = "test@example.com",
            password = "{bcrypt}$2a$10\$PLbRTV7qLT2xJgcV8TU3AeiwyEzbtIa/LDIFhG3yzlTlIzfrUs5fW",
            companyName = "TestCompany",
            roleInCompany = "employee",
            industry = "IT",
            registrationState = RegistrationState.CONFIRMED,
            comment = "",
            roles = mutableSetOf(Role.ADMIN)
        )
        val domainUserDetails = AuthenticatedUser(user)

        // When / Then
        assertEquals(user.email, domainUserDetails.username)
        assertEquals(user.password, domainUserDetails.password)
        assertTrue(domainUserDetails.isAccountNonExpired)
        assertTrue(domainUserDetails.isAccountNonLocked)
        assertTrue(domainUserDetails.isCredentialsNonExpired)
        assertTrue(domainUserDetails.isEnabled)
    }
}