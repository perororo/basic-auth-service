package com.basic.auth.basic.auth.service.common.domain

import java.time.ZoneId
import java.time.ZonedDateTime
import org.springframework.stereotype.Service

@Service
class TimeProvider {
    fun now() = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"))
}
