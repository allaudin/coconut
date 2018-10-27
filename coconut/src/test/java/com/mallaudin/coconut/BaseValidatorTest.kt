package com.mallaudin.coconut

import com.mallaudin.coconut.validation.BaseValidationProvider
import com.mallaudin.coconut.validation.ValidationProvider
import com.mallaudin.coconut.validation.Validator
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class BaseValidatorTest {

    private lateinit var validationProvider: ValidationProvider

    @Before
    fun setUp() {
        validationProvider = object : BaseValidationProvider() {
            override fun addValidators(): MutableMap<String, Validator> {
                val validators: MutableMap<String, Validator> = HashMap()
                validators["    a   "] = { true }
                return validators
            }
        }
    }

    @Test(expected = IllegalStateException::class)
    fun illegalSate_When_GetByKey_Called_On_Un_Initialized_Provider() {
       validationProvider.getByKey("a")
    }

    @Test
    fun verify_Keys_Are_Trimmed_Before_Adding_To_Map() {
        validationProvider.init()
        assertNotNull(validationProvider.getByKey("a"))
    }
}