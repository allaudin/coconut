package com.mallaudin.coconut


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DefaultValidationProviderTest {


    private val provider: CoconutValidationProvider = CoconutValidationProvider.get()

    @Test
    fun test_Non_Empty_Validator() {
        val nonEmptyValidator = provider.getByKey("non_empty")
        assertNotNull(nonEmptyValidator)
        nonEmptyValidator?.let { validate ->
            val invalidInputs = validate(null)
                    || validate("") || validate("   ")
            assertEquals(false, invalidInputs)
            assertEquals(true, validate("foo"))
        }

    }

    @Test
    fun test_Valid_Email_Validator() {
        val emailValidator = provider.getByKey("valid_email")
        assertNotNull(emailValidator)
        emailValidator?.let { validate ->
            val invalidInputs = validate(null)
                    || validate("") || validate("foo")

            assertEquals(false, invalidInputs)
            assertEquals(true, validate("foo@gmail.com"))
        }

    }
}