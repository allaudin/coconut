package com.mallaudin.coconut.widget

import android.content.Context
import android.graphics.Color
import android.support.annotation.StringRes
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import io.com.mallaudin.coconut.R



abstract class BaseErrorAwareEt : LinearLayout, Input, CoconutView {

    override var validatorKey: String? = null
    override var isOptional: Boolean = false
    override var errorMessage: String? = null
        get() = field ?: ""

    lateinit var editTextView: EditText
    lateinit var errorView: TextView


    constructor(ctx: Context) : super(ctx) {
        init(ctx)
    }

    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs) {
        init(context, attrs)
    }

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(ctx, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BaseErrorAwareEt,
                defStyleAttr, 0)

        val errorColor: Int
        val errorTextSize: Int

        try {

            errorMessage = typedArray.getString(
                    R.styleable.BaseErrorAwareEt_cnt_error_message)
            isOptional = typedArray.getBoolean(
                    R.styleable.BaseErrorAwareEt_cnt_optional, false)
            validatorKey = typedArray.getString(
                    R.styleable.BaseErrorAwareEt_cnt_validator)

            errorColor = typedArray.getColor(
                    R.styleable.BaseErrorAwareEt_cnt_error_text_color, Color.RED)
            errorTextSize = typedArray.getDimensionPixelSize(
                    R.styleable.BaseErrorAwareEt_cnt_error_text_size, -1)
        } finally {
            typedArray.recycle()
        }

        orientation = VERTICAL
        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        editTextView = provideEditText(context, attrs, R.attr.editTextStyle)
        editTextView.id = -1

        errorView = provideErrorTextView(context)
        errorView.visibility = View.GONE
        errorView.setTextColor(errorColor)

        if (errorTextSize != -1) {
            errorView.setTextSize(TypedValue.COMPLEX_UNIT_PX, errorTextSize.toFloat())
        }

        addView(editTextView, params)
        addView(errorView, params)

        setWatcher(this)
    }

    override val value: String?
        get() {
            val text = editTextView.text
            return text?.toString()
        }


    val text: Editable
        get() = editTextView.text

    override val input: Input?
        get() = this


    fun setText(text: CharSequence) {
        editTextView.setText(text)
    }

    fun setText(@StringRes stringResId: Int) {
        editTextView.setText(stringResId)
    }

    override fun setTextWatcher(watcher: CoconutTextWatcher) {
        editTextView.addTextChangedListener(watcher)
    }

    override fun setErrorMessage(error: CharSequence?) {
        errorView.visibility = View.VISIBLE
        errorView.text = error
    }

    override fun removeError() {
        errorView.visibility = View.GONE
        errorView.text = null
    }

    override fun setWatcher(inputView: Input?) {
        inputView?.setTextWatcher(object : CoconutTextWatcher(this) {
            override fun onTextChange(view: CoconutView) {
                view.removeError()
            }
        })
    }

    abstract fun provideEditText(context: Context,
                                 attrs: AttributeSet? = null,
                                 defStyleAttr: Int = 0): EditText

    abstract fun provideErrorTextView(context: Context): TextView
}
