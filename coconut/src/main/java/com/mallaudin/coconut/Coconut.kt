package com.mallaudin.coconut

import android.view.ViewGroup
import com.mallaudin.coconut.validation.ValidationProvider
import com.mallaudin.coconut.validation.ValidatorNotFound
import com.mallaudin.coconut.widget.CoconutView

class Coconut private constructor(val provider: ValidationProvider,
                                  private val debug: Boolean = false,
                                  private val logger: Logger? = null) {


    companion object {

        private var instance: Coconut? = null

        @JvmOverloads
        fun init(provider: ValidationProvider, debug: Boolean = false, logger: Logger? = null) {
            if (instance == null) {
                instance = Coconut(provider.init(), debug, logger)
            }
        }

        fun get(): Coconut {
            if (instance == null) {
                throw IllegalStateException("Coconut is not initialized. Make sure " +
                        "you call Coconut.init(ValidationProvider) once before accessing " +
                        " it via get()")
            }
            return instance!!
        }

        fun destroy() {
            instance = null
        }
    }

    /**
     * Validates [CoconutView]s based on the given validator key and shows
     * respective error messages for invalid inputs.
     *
     * @param views views to be validated
     * @return true - if all inputs are valid, false otherwise
     */
    fun areFieldsValid(vararg views: CoconutView): Boolean {

        var areFieldsValid = true

        for (view in views) {
            val inputView = view.input

            if (inputView == null || inputView.isOptional) {
                continue
            }


            val input = inputView.value
            val errorMessage = inputView.errorMessage

            val validatorKeys = tokenizeKey(inputView.validatorKey)
                    ?: throw IllegalStateException("validatorKey is null for non-optional input view")


            for (key in validatorKeys) {
                val validator = provider.getByKey(key.trim())
                        ?: throw ValidatorNotFound("No validator found with key: $key. " +
                                "Have you misspelled validator key in layout?")


                val isValid = validator.invoke(input)
                log(input, errorMessage, key, isValid)

                if (!isValid) {
                    areFieldsValid = false
                    view.setErrorMessage(errorMessage ?: "")
                }
            }


        }
        return areFieldsValid
    }  // areFieldsValid


    private fun tokenizeKey(input: String?) = input?.trim()
            ?.replace("||", "|")
            ?.split("|")

    /**
     * Validates all [CoconutView]s in this parent
     *
     * @param parent parent view group containing [CoconutView]s
     * @return true - if all inputs are valid, false otherwise
     */
    fun validateLayoutFields(parent: ViewGroup): Boolean {
        val views = ArrayList<CoconutView>()
        getViews(parent, views)
        return areFieldsValid(*views.toTypedArray())
    }


    /**
     * Collect [CoconutView] recursively from the root view.
     *
     * @param root     root view group which contains [CoconutView]s.
     * @param viewList list of collected views
     */
    private fun getViews(root: ViewGroup, viewList: MutableList<CoconutView>) {
        for (i in 0..root.childCount) {
            val view = root.getChildAt(i)
            if (view is CoconutView) {
                viewList.add(view)
            } else if (view is ViewGroup) {
                getViews(view, viewList)
            }
        }

    } // getViews

    private fun log(input: String?, errorMessage: String?, key: String, isValid: Boolean) {
        if (debug) {
            val msg = "\nValidating...\n" +
                    "Input = $input\n" +
                    "ErrorMessage = $errorMessage\n" +
                    "Validator = $key\n" +
                    "IsValid = $isValid\n"
            logger?.log(msg)
        }
    }

}