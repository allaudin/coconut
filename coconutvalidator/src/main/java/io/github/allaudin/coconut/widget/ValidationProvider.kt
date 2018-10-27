package io.github.allaudin.coconut.widget

interface ValidationProvider {

    fun getByKey(key: String): ((input: String?) -> Boolean)?
}



