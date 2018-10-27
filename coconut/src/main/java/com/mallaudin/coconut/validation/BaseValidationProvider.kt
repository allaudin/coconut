package com.mallaudin.coconut.validation


abstract class BaseValidationProvider : ValidationProvider {


    private var validatorMap: MutableMap<String, (input: String?) -> Boolean> = HashMap()

    final override fun init(): ValidationProvider {
        validatorMap.clear()
        for ((key, validator) in addValidators()) {
            validatorMap[key] = validator
        }
        return this
    }


    final override fun getByKey(key: String) = validatorMap[key]

    abstract fun addValidators(): Map<String, (input: String?) -> Boolean>
}