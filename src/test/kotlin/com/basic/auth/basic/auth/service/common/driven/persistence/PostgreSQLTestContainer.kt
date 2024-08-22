package com.basic.auth.basic.auth.service.common.driven.persistence

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface PostgreSQLTestContainer {
    companion object {
        @JvmStatic
        @Container
        @ServiceConnection
        @Suppress("unused")
        val postgres = PostgreSQLContainer("postgres:16-alpine")
    }
}