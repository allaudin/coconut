package io.github.allaudin.coconut.widget

import android.text.Editable
import android.text.TextWatcher


/**
 * Text watcher for removing errors form [CoconutView] after
 * text is changed.
 *
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 */

abstract class CoconutTextWatcher(private val view: CoconutView) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // no-op
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // no-op
    }

    override fun afterTextChanged(s: Editable) {
        onTextChange(view)
    }

    /**
     * Called when text change is detected on [Input] of
     * [CoconutView]
     *
     * @param view current view
     */
    protected abstract fun onTextChange(view: CoconutView)
}
