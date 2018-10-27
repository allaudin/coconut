package com.mallaudin.coconut.widget

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet
import io.com.mallaudin.coconut.R



/**
 * A specialized [Input] which contains information about
 * it's validation regex, error message and either field is optional or not.
 *
 *
 * This must be used as a  child layout in [CoconutTextInputLayout]
 *
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 * @see CoconutTextInputLayout
 */

class CoconutInputEditText : TextInputEditText, Input {

    override var errorMessage: String? = null
        get() = field ?: ""
    override var isOptional: Boolean = false
    override var validatorKey: String? = null

    override val value: String?
        get() {
            val text = text
            return text?.toString()
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CoconutInputEditText,
                defStyleAttr, 0)

        try {
            errorMessage = typedArray.getString(
                    R.styleable.CoconutInputEditText_cnt_error_message)
            validatorKey = typedArray.getString(
                    R.styleable.CoconutInputEditText_cnt_validator)
            isOptional = typedArray.getBoolean(
                    R.styleable.CoconutInputEditText_cnt_optional, false)
        } finally {
            typedArray.recycle()
        }
    }


    override fun setTextWatcher(watcher: CoconutTextWatcher) {
        addTextChangedListener(watcher)
    }

} // CoconutInputEditText
