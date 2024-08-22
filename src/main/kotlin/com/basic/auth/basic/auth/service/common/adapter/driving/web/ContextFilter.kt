package com.basic.auth.basic.auth.service.common.adapter.driving.web

import com.basic.auth.basic.auth.service.common.adapter.common.RequestId
import com.basic.auth.basic.auth.service.common.adapter.common.ThreadContext
import com.basic.auth.basic.auth.service.common.adapter.common.ThreadContextService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ContextFilter(private val threadContextService: ThreadContextService) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestId = request.getHeader(RequestId.headerName)
            ?.let(::RequestId)
            ?: RequestId.random()

        threadContextService.runWithNewContext(ThreadContext(requestId)) {
            filterChain.doFilter(request, response)
        }
    }
}