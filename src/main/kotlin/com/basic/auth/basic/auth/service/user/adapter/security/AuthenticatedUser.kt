package com.basic.auth.basic.auth.service.user.adapter.security

import com.basic.auth.basic.auth.service.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(private val user: User) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = mutableSetOf<GrantedAuthority>()

        user.roles.forEach { role ->
            authorities.addAll(role.permissions.map { SimpleGrantedAuthority("PERMISSION_${it.name}") })
            authorities.add(SimpleGrantedAuthority("ROLE_${role.name}"))
        }

        return authorities
    }

    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}