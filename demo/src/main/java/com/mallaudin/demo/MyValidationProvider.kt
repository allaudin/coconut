package com.mallaudin.demo

import com.mallaudin.coconut.validation.CoconutValidationProvider
import com.mallaudin.coconut.validation.Validator

class MyValidationProvider : CoconutValidationProvider() {
    override fun addValidators(): MutableMap<String, Validator> {
        val validators = super.addValidators()
        validators["length_7"] = { it?.length == 7 }
        return validators
    }
}