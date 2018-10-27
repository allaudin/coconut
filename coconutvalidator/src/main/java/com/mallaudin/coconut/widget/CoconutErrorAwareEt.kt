package com.mallaudin.coconut.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView


class CoconutErrorAwareEt : BaseErrorAwareEt {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(ctx, attrs, defStyleAttr)

    override fun provideEditText(context: Context, attrs: AttributeSet?,
                                 defStyleAttr: Int): EditText {
        return EditText(context, attrs, defStyleAttr)
    }

    override fun provideErrorTextView(context: Context) = TextView(context)
}
