package com.mallaudin.demo

import com.mallaudin.coconut.validation.CoconutValidationProvider
import com.mallaudin.coconut.validation.Validator

class MyValidationProvider : CoconutValidationProvider() {

    /**
     * Allows you to add your own validator against keys. Here the
     * <b>length_7</b> is the key you need to provider in `XML` to validate
     * that input has exactly 7 characters.
     */
    override fun addValidators(): MutableMap<String, Validator> {
        val validators = super.addValidators() // get default validators
        validators["length_7"] = { length7(it) }
        return validators
    }

    private fun length7(input: String?) = input?.length == 7
}