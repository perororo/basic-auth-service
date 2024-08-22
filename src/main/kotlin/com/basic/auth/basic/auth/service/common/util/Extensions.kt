package com.basic.auth.basic.auth.service.common.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Optional

fun <T : Any> Optional<T>.toNullable(): T? = orElse(null)
fun Double.degToRad() = this * Math.PI / 180

fun LocalDateTime.toUtcZonedDateTime() = ZonedDateTime.of(this, utcZoneId())
fun ZonedDateTime.toUtcLocalDateTime() = this.withZoneSameInstant(utcZoneId()).toLocalDateTime()

fun utcZoneId() = ZoneId.of("UTC")