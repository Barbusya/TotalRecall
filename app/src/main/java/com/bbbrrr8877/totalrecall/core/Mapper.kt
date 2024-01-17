package com.bbbrrr8877.totalrecall.core

interface Mapper<S : Any, R : Any> {

    fun map(data: S): R

    interface Unit<S : Any> : Mapper<S, kotlin.Unit>
}