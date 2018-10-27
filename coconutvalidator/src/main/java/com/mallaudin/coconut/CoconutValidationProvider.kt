package com.mallaudin.coconut


import com.mallaudin.coconut.widget.ValidationProvider
import java.util.regex.Pattern

class CoconutValidationProvider private constructor() : ValidationProvider {

    private var validatorMap: MutableMap<String, (input: String?) -> Boolean> = HashMap()

    init {
        validatorMap["non_empty"] = { isNonEmpty(it) }
        validatorMap["valid_email"] = { isValidEmail(it) }
        validatorMap["digits_only"] = { digitsOnly(it) }
    }

    companion object Factory {
        fun get() = CoconutValidationProvider()
    }

    override fun getByKey(key: String) = validatorMap[key]

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


}