package com.mallaudin.coconut

import java.util.regex.Pattern

class CoconutValidationProvider : BaseValidationProvider() {

    override fun addValidators(): Map<String, (input: String?) -> Boolean> {
        val validatorMap: MutableMap<String, (input: String?) -> Boolean> = HashMap()
        validatorMap["non_empty"] = { isNonEmpty(it) }
        validatorMap["valid_email"] = { isValidEmail(it) }
        validatorMap["digits_only"] = { digitsOnly(it) }
        validatorMap["letters_only"] = { lettersOnly(it) }
        return validatorMap;
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