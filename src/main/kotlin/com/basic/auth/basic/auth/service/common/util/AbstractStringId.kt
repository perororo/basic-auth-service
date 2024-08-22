package com.basic.auth.basic.auth.service.common.util

abstract class AbstractStringId(val value: String) {


    override fun toString(): String {
        return "${this::class.simpleName}($value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractStringId

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}