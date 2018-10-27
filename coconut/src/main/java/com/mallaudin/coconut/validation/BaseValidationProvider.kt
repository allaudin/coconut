package com.mallaudin.coconut.validation


abstract class BaseValidationProvider : ValidationProvider {

    private var isInitialized = false
    private val validatorMap: MutableMap<String, Validator> = HashMap()

    final override fun init(): ValidationProvider {
        isInitialized = true
        validatorMap.clear()
        for ((key, validator) in addValidators()) {
            validatorMap[key.trim()] = validator
        }
        return this
    }


    final override fun getByKey(key: String): Validator {
        if (!isInitialized) {
            throw IllegalStateException("ValidationProvider.getByKey(String) " +
                    "is called on uninitialized ValidationProvider")
        }
        return validatorMap[key]
    }

    abstract fun addValidators(): MutableMap<String, Validator>
}