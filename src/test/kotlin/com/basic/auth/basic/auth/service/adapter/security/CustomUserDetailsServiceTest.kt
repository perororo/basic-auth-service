package com.basic.auth.basic.auth.service.adapter.security

import com.basic.auth.basic.auth.service.user.adapter.security.CustomUserDetailsService
import com.basic.auth.basic.auth.service.user.adapter.security.Role
import com.basic.auth.basic.auth.service.user.model.RegistrationState
import com.basic.auth.basic.auth.service.user.model.User
import com.basic.auth.basic.auth.service.user.port.driven.persistence.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.UUID

class CustomUserDetailsServiceTest {

    private val userRepository: UserRepository = mockk()
    private val customUserDetailsService: CustomUserDetailsService = CustomUserDetailsService(userRepository)

    @Test
    fun `loadUserByUsername should return UserDetails when user is found`() {
        // Given
        val email = "test@example.com"
        val user = User(
            id = UUID.randomUUID(),
            firstName = "Test",
            lastName = "User",
            email = email,
            password = "{bcrypt}$2a$10\$PLbRTV7qLT2xJgcV8TU3AeiwyEzbtIa/LDIFhG3yzlTlIzfrUs5fW",
            companyName = "TestCompany",
            roleInCompany = "employee",
            industry = "IT",
            registrationState = RegistrationState.CONFIRMED,
            comment = "",
            roles = mutableSetOf(Role.ADMIN)
        )
        every { userRepository.findByEmail(email) } returns user

        // When
        val userDetails = customUserDetailsService.loadUserByUsername(email)
        val expectedAuthorities = mutableSetOf<SimpleGrantedAuthority>()

        // Then
        assertNotNull(userDetails)
        assertEquals(email, userDetails.username)
        assertEquals(user.password, userDetails.password)

        user.roles.forEach { role ->
            expectedAuthorities.addAll(role.permissions.map { SimpleGrantedAuthority("PERMISSION_${it.name}") })
            expectedAuthorities.add(SimpleGrantedAuthority("ROLE_${role.name}"))
        }

        // Perform the assertion
        assertEquals(expectedAuthorities, userDetails.authorities.toSet())

        verify { userRepository.findByEmail(email) }
    }

    @Test
    fun `loadUserByUsername should throw UsernameNotFoundException when user is not found`() {
        // Given
        val email = "nonexistent@example.com"
        every { userRepository.findByEmail(email) } returns null

        // When / Then
        assertThrows(UsernameNotFoundException::class.java) {
            customUserDetailsService.loadUserByUsername(email)
        }

        verify { userRepository.findByEmail(email) }
    }
}
