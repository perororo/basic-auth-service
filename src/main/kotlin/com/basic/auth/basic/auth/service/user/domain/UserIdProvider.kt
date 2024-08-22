package com.basic.auth.basic.auth.service.user.domain

import com.basic.auth.basic.auth.service.user.model.UserId
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserIdProvider {
    fun nextId() = UserId(UUID.randomUUID().toString())
}