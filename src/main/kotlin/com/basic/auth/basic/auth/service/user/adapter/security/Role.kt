package com.basic.auth.basic.auth.service.user.adapter.security

enum class Role(val permissions: Set<Permission>) {
    END_USER(setOf(Permission.READ_REVIEWS, Permission.WRITE_REVIEWS, Permission.USE_ASSISTANCE)),
    ADMIN(
        setOf(
            Permission.READ_USER_ADMIN,
            Permission.WRITE_USER_ADMIN,
            Permission.READ_REVIEWS,
            Permission.WRITE_REVIEWS,
            Permission.USE_ASSISTANCE
        )
    )
}