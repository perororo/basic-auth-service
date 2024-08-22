package com.basic.auth.basic.auth.service.user.port.driven.persistence

import com.basic.auth.basic.auth.service.user.model.User
import java.util.UUID

interface UserRepository {
    fun findById(id: UUID): User?
    fun findAll(): Set<User>
    fun createOrUpdate(user: User): User
    fun findByEmail(email: String): User?
}
