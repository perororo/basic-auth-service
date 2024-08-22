package com.basic.auth.basic.auth.service.common.adapter.common

import com.basic.auth.basic.auth.service.common.util.AbstractStringId
import java.util.UUID

class RequestId(value: String): AbstractStringId(value) {

    companion object{
        fun random() = RequestId(UUID.randomUUID().toString())

        const val headerName = "X-Request-Id"
    }
}