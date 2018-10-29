package com.mallaudin.coconut.validation

/**
 * Thrown when a key defined in layout does not match
 * any [Validator] provided by [com.mallaudin.coconut.validation.ValidationProvider]
 */
class ValidatorNotFound(message: String) : RuntimeException(message)
