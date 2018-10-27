package com.mallaudin.coconut


import com.mallaudin.coconut.widget.ValidationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CoconutValidationProviderTest {


    private val provider: ValidationProvider = CoconutValidationProvider().init()

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

    @Test
    fun test_Digits_Only() {
        val digitValidator = provider.getByKey("digits_only")
        assertNotNull(digitValidator)
        digitValidator?.let { validate ->
            val invalidInputs = validate(null)
                    || validate("") || validate("foo")

            assertEquals(false, invalidInputs)
            assertEquals(true, validate("123456"))
        }

    }

    @Test
    fun test_Letters_Only() {
        val lettersOnly = provider.getByKey("letters_only")
        assertNotNull(lettersOnly)
        lettersOnly?.let { validate ->
            val invalidInputs = validate(null)
                    || validate("") || validate("123")
                    || validate("!@#$%&^())*")

            assertEquals(false, invalidInputs)
            assertEquals(true, validate("abc"))
            assertEquals(true, validate("ñÑá"))
        }

    }
}