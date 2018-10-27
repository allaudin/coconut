package com.mallaudin.coconut


import com.mallaudin.coconut.validation.ValidationProvider
import com.mallaudin.coconut.validation.ValidatorNotFound
import com.mallaudin.coconut.validation.Validator
import com.mallaudin.coconut.widget.CoconutView
import com.mallaudin.coconut.widget.Input
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner

/**
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 */

@RunWith(MockitoJUnitRunner::class)
class CoconutTest {

    private lateinit var coconut: Coconut

    @Mock
    private lateinit var input: Input

    @Mock
    private lateinit var coconutView: CoconutView

    @Mock
    private lateinit var validationProvider: ValidationProvider

    @Mock
    private var validator: Validator = null


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(validationProvider.getByKey(VALIDATOR_KEY))
                .thenReturn(validator)
        `when`(validationProvider.init())
                .thenReturn(validationProvider)

        Coconut.init(validationProvider)
        coconut = Coconut.get()
    }

    @After
    fun cleanUp() {
        Coconut.destroy()
    }


    @Test(expected = IllegalStateException::class)
    fun field_Is_Invalid_For_NonOptional_Null_Input() {
        `when`(coconutView.input).thenReturn(input)
        coconut.areFieldsValid(coconutView)

    }

    @Test(expected = ValidatorNotFound::class)
    fun nullValidator_Throws_ValidatorNotFound_Exception() {
        `when`(coconutView.input).thenReturn(input)
        `when`(input.validatorKey).thenReturn("")
        coconut.areFieldsValid(coconutView)
    }


    @Test
    fun field_Is_Valid_For_Optional_Or_Null_InputViews() {
        // true for null inputView
        assertTrue(coconut.areFieldsValid(coconutView))

        `when`(coconutView.input).thenReturn(input)
        `when`(input.isOptional).thenReturn(true)

        // true if only optional fields are there
        assertTrue(coconut.areFieldsValid(coconutView))

        verify(input, times(1))
                .isOptional
    }


    @Test
    fun multiple_Validators_Are_Applied_For_Piped_Key() {

        `when`(input.validatorKey).thenReturn("a|b|c")
        `when`(coconutView.input).thenReturn(input)
        `when`(validationProvider.getByKey(anyString())).thenReturn(validator)
        `when`(validator?.invoke(Matchers.anyString())).thenReturn(true)
        coconut.areFieldsValid(coconutView)

        verify(validationProvider, times(3))
                .getByKey(anyString())
        verify(validator, times(3))
                ?.invoke(Matchers.anyString())

        verify(validationProvider).getByKey("a")
        verify(validationProvider).getByKey("b")
        verify(validationProvider).getByKey("c")
    }

    @Test
    fun doublePipe_And_Empty_Start_Last_Spaces_Are_Removed(){
        `when`(input.validatorKey).thenReturn("    a||b||c")
        `when`(coconutView.input).thenReturn(input)
        `when`(validationProvider.getByKey(anyString())).thenReturn(validator)
        `when`(validator?.invoke(Matchers.anyString())).thenReturn(true)
        coconut.areFieldsValid(coconutView)
        verify(validationProvider, times(3))
                .getByKey(anyString())
        verify(validator, times(3))
                ?.invoke(Matchers.anyString())
    }

    @Test
    fun validation_Is_Performed_Correctly() {

        val inputMessage = "Foo"
        val errorMessage = "Bar"

        `when`(input.errorMessage).thenReturn(errorMessage)
        `when`(input.value).thenReturn(inputMessage)
        `when`(coconutView.input).thenReturn(input)
        `when`(input.validatorKey).thenReturn(VALIDATOR_KEY)
        `when`(validator?.invoke(inputMessage)).thenReturn(true)
        var isValidField = coconut.areFieldsValid(coconutView)

        // check true if validator returns true for a field
        verify(validationProvider).getByKey(VALIDATOR_KEY)
        verify(coconutView, times(0))
                .setErrorMessage(anyString())
        verify(validator)?.invoke(inputMessage)
        assertTrue(isValidField)

        // check false if validator returns false for a field
        `when`<Boolean>(validator?.invoke(inputMessage)).thenReturn(false)
        isValidField = coconut.areFieldsValid(coconutView)
        verify(coconutView, times(1))
                .setErrorMessage(errorMessage)
        assertFalse(isValidField)

    }

    @Test
    fun validate_Set_Error_Message_String_Is_Always_NonNull() {

        `when`(input.errorMessage).thenReturn(null)
        `when`(input.value).thenReturn("")
        `when`(coconutView.input).thenReturn(input)
        `when`(input.validatorKey).thenReturn(VALIDATOR_KEY)
        `when`(validator?.invoke("")).thenReturn(false)
        coconut.areFieldsValid(coconutView)
        verify(coconutView, times(1)).setErrorMessage("")
    }

    @Test
    fun verify_Provider_Is_Initialized_On_Coconut_Init() {
        Coconut.destroy()
        val provider = Mockito.mock(ValidationProvider::class.java)
        Coconut.init(provider)
        verify(provider, times(1)).init()
    }

    companion object {
        private const val VALIDATOR_KEY = "foo_bar"
    }
} // cValidTest