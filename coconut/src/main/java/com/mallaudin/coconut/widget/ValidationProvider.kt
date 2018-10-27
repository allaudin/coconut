package com.mallaudin.coconut.widget

interface ValidationProvider {

    fun init(): ValidationProvider

    fun getByKey(key: String): ((input: String?) -> Boolean)?
}



