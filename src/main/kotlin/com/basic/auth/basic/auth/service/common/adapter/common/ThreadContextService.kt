package com.basic.auth.basic.auth.service.common.adapter.common

import com.basic.auth.basic.auth.service.common.util.LoggingContext
import org.springframework.stereotype.Component

@Component
class ThreadContextService {

    private val holder = ThreadLocal<ThreadContext>()


    fun set(value: ThreadContext){
        holder.set(value)
        LoggingContext.putId(value.requestId)
    }

    fun get(): ThreadContext? {
        return holder.get()
    }

    fun clear(){
        holder.remove()
    }

    fun <T>  runWithNewContext(
        threadContext: ThreadContext = ThreadContext(requestId = RequestId.random()),
        block: () -> T
    ): T{
        try {
            set(threadContext)
            return block()
        }finally {
            clear()
        }
    }
}

