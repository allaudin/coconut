package com.mallaudin.coconut.validation

/**
 * Validation provider interface for [com.mallaudin.coconut.widget.Input].
 * It is responsible for providing a validator against a specific key
 */
interface ValidationProvider {

    /**
     * Provider initializer, responsible for collecting validators and
     * saving them against respective keys
     */
    fun init(): ValidationProvider

    /**
     * Providers [Validator] against key
     *
     * @return [Validator] for given key
     */
    fun getByKey(key: String): Validator
}



