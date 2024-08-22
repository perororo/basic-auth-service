package com.basic.auth.basic.auth.service.common.adapter.driving.web

import com.basic.auth.basic.auth.service.common.driven.persistence.PostgreSQLTestContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post

@SpringBootTest(
    classes = [
        // configure a new application class so the spring boot test only tests the adapter and not include other
        // configurations ...
        AbstractControllerTest.App::class,
        // here you can include additional configuration eg. serialisation or exception handling
        CommonExceptionHandler::class]
)
@AutoConfigureMockMvc
@ImportTestcontainers(PostgreSQLTestContainer::class)
abstract class AbstractControllerTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    // try to create opinionated convenience methods, so it is easy to follow the conventions ...
    fun MockMvc.postJson(uri: String, json: String) = post(uri) {
        contentType = MediaType.APPLICATION_JSON
        content = json.trimIndent()
    }

    fun ResultActionsDsl.andExpectJson(
        status: HttpStatus = HttpStatus.OK,
        contentType: MediaType = MediaType.APPLICATION_JSON,
        json: String,
    ) {
        andExpect {
            content {
                status {
                    isEqualTo(status.value())
                }
                json(
                    jsonContent = json.trimIndent(), strict = true
                )
                contentType(contentType)
            }
        }
    }

    @SpringBootApplication
    class App {}
}
