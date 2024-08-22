package com.basic.auth.basic.auth.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BasicAuthServiceApplication

fun main(args: Array<String>) {
	runApplication<BasicAuthServiceApplication>(*args)
}
