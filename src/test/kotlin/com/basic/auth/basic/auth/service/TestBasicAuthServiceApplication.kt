package com.basic.auth.basic.auth.service

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<BasicAuthServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
