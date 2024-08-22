package com.basic.auth.basic.auth.service.web.adapter.driving.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/test-controller")
class TestController() {

    @GetMapping("hello")
    fun findNear(
    ): ResponseEntity<Any> {
        return ResponseEntity.ok().body("this was ok")
    }

}
