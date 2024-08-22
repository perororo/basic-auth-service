package com.basic.auth.basic.auth.service.common.adapter.driving.web

import com.basic.auth.basic.auth.service.common.util.utcZoneId
import java.time.LocalDateTime
import java.time.ZonedDateTime

fun localDateTime(year: Int, month: Int, day: Int, hours: Int = 0, mins: Int = 0, seconds: Int = 0, millis: Int = 0): LocalDateTime{
    return LocalDateTime.of(year, month, day, hours, mins, seconds, millis)
}

fun utcDateTime(year: Int, month: Int, day: Int, hours: Int = 0, mins: Int = 0, seconds: Int = 0, millis: Int = 0): ZonedDateTime{
    return ZonedDateTime.of(LocalDateTime.of(year, month, day, hours, mins, seconds, millis), utcZoneId())
}