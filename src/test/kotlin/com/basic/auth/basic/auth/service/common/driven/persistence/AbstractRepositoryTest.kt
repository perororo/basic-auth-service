package com.basic.auth.basic.auth.service.common.driven.persistence

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [AbstractRepositoryTest.App::class])
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ImportTestcontainers(PostgreSQLTestContainer::class)
abstract class AbstractRepositoryTest {

    @Autowired
    protected lateinit var entityManager: EntityManager


    protected fun flushAndClear(){
        entityManager.flush()
        entityManager.clear()
    }

    @SpringBootApplication
    class App
}