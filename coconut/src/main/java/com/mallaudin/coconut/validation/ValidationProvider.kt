package com.mallaudin.coconut.validation

interface ValidationProvider {

    fun init(): ValidationProvider

    fun getByKey(key: String): ((input: String?) -> Boolean)?
}



