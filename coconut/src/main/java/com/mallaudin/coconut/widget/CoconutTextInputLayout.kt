package com.mallaudin.coconut.widget

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet

/**
 * A parent wrapper for [CoconutTextInputLayout] responsible for
 * setting and removing error messages when validated via
 * [com.mallaudin.coconut.Coconut]
 *
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 * @see com.mallaudin.coconut.Coconut.areFieldsValid
 */

class CoconutTextInputLayout : TextInputLayout, CoconutView {

    private var watcher: CoconutTextWatcher? = null

    override val input: Input?
        get() {
            val input = editText as? Input?
            setWatcher(input)
            return input
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


    override fun removeError() {
        isErrorEnabled = false
        error = null
    }

    override fun setWatcher(inputView: Input?) {
        if (inputView == null || watcher != null) {
            return
        }
        watcher = object : CoconutTextWatcher(this) {
            override fun onTextChange(view: CoconutView) {
                view.removeError()
            }
        }
        inputView.setTextWatcher(watcher!!)
    }

    override fun setErrorMessage(error: CharSequence?) {
        isErrorEnabled = true
        setError(error)
    }
}
