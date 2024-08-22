package com.basic.auth.basic.auth.service.common.util

import org.slf4j.MDC
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

class LoggingContext(private val context: Map<String, String>) {

    fun apply() {
        MDC.setContextMap(context)
    }

    companion object {

        val Empty = LoggingContext(emptyMap())

        fun capture(): LoggingContext {
            return LoggingContext(MDC.getCopyOfContextMap() ?: emptyMap())
        }

        fun putUseCase(callable: KFunction<*>) {
            val prefix = callable.javaMethod?.declaringClass?.simpleName?.let { "$it." }
            putUseCase("$prefix${callable.name}")
        }

        fun putUseCase(useCaseName: String) {
            put("UseCase", useCaseName)
        }

        fun put(name: String, value: String) {
            MDC.put(name, value)
        }

        fun putId(id: AbstractStringId) {
            MDC.put(id::class.simpleName, id.value)
        }

        fun <T> new(block: () -> T): T {
            val old = capture()
            try {
                Empty.apply()
                return block()
            } finally {
                old.apply()
            }
        }

        fun <T> unit(block: () -> T): T {
            val old = MDC.getCopyOfContextMap()
            try {
                return block()
            } finally {
                MDC.setContextMap(old)
            }
        }
    }

}