package com.mallaudin.coconut

import android.view.ViewGroup
import com.mallaudin.coconut.validation.ValidationProvider
import com.mallaudin.coconut.validation.ValidatorNotFound
import com.mallaudin.coconut.widget.CoconutView

class Coconut private constructor(val provider: ValidationProvider) {

    companion object {
        private var instance: Coconut? = null

        fun init(provider: ValidationProvider) {
            if (instance == null) {
                instance = Coconut(provider.init())
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
     * Validates [CoconutView]s based on the given regex and shows
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
                val validator = provider.getByKey(key)
                        ?: throw ValidatorNotFound("No validator found with key: $key. " +
                                "Have you misspelled validator key in layout?")


                if (!validator.invoke(input)) {
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
}