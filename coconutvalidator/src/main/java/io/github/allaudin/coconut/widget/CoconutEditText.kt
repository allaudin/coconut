package io.github.allaudin.coconut.widget

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import io.github.allaudin.coconut.R


/**
 * An error aware edit text which contains validation regex for validating it's
 * input data and error message to be displayed when an incorrect data is detected
 * via [io.github.allaudin.coconut.Coconut]
 *
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 */

class CoconutEditText : AppCompatEditText, Input, CoconutView {

    override var isOptional: Boolean = false
    override var validatorKey: String? = null
    override var errorMessage: String? = null
        get() = field ?: ""

    override val input: Input?
        get() = this

    override val value: String?
        get() = text?.toString()


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?)
            : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CoconutEditText,
                defStyleAttr, 0)
        try {
            errorMessage = typedArray.getString(
                    R.styleable.CoconutEditText_cnt_error_message)
            validatorKey = typedArray.getString(
                    R.styleable.CoconutEditText_cnt_validator)
            isOptional = typedArray.getBoolean(
                    R.styleable.CoconutEditText_cnt_optional, false)
        } finally {
            typedArray.recycle()
        }
        setWatcher(this)
    }


    override fun setErrorMessage(error: CharSequence?) {
        setError(error)
    }

    override fun removeError() {
        error = null
    }

    override fun setWatcher(inputView: Input?) {
        inputView?.setTextWatcher(object : CoconutTextWatcher(this) {
            override fun onTextChange(view: CoconutView) {
                view.removeError()
            }
        })
    }

    override fun setTextWatcher(watcher: CoconutTextWatcher) {
        addTextChangedListener(watcher)
    }

}
