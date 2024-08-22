package com.basic.auth.basic.auth.service.common.model

import java.util.*

sealed class UpdatableValue<T>{

    data class Update<T>(val value: T): UpdatableValue<T>()
    class Keep<T>: UpdatableValue<T>()
}

fun <T: Any?>Optional<T>?.toUpdatableNullableValue()= if(this == null){
    UpdatableValue.Keep()
}else{
    UpdatableValue.Update(this.orElse(null))
}

fun <T: Any>Optional<T>?.toUpdatableValue(): UpdatableValue<T> = if(this == null){
    UpdatableValue.Keep()
}else{
    UpdatableValue.Update(this.orElseThrow())
}


fun <T> UpdatableValue<T>.updateOrCurrent(current: T): T = when(this){
    is UpdatableValue.Keep -> current
    is UpdatableValue.Update -> value
}