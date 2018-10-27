package com.mallaudin.coconut.widget

interface ValidationProvider {

    fun getByKey(key: String): ((input: String?) -> Boolean)?
}



