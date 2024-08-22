package com.basic.auth.basic.auth.service.common.exceptions

import java.lang.RuntimeException
import kotlin.reflect.KClass

class NotFoundException(val objectType: KClass<*>, val id: Any) :
    RuntimeException("${objectType.simpleName} for $id was not found")
