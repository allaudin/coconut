package com.mallaudin.coconut

import java.util.regex.Pattern

class CoconutValidationProvider : BaseValidationProvider() {

    override fun addValidators(): Map<String, (input: String?) -> Boolean> {
        val validators: MutableMap<String, (input: String?) -> Boolean> = HashMap()
        validators["non_empty"] = { isNonEmpty(it) }
        validators["valid_email"] = { isValidEmail(it) }
        validators["digits_only"] = { digitsOnly(it) }
        validators["letters_only"] = { lettersOnly(it) }
        return validators
    }

    private fun isNonEmpty(input: String?) =
            input != null && !input.trim { it <= ' ' }.isEmpty()

    private fun isValidEmail(input: String?): Boolean {
        val pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )
        return isNonEmpty(input) && pattern.matcher(input).matches()
    }

    private fun digitsOnly(input: String?) = isNonEmpty(input)
            && input!!.matches(Regex("\\d+"))

    private fun lettersOnly(input: String?) = isNonEmpty(input)
            && input!!.matches(Regex("\\pL+"))
}