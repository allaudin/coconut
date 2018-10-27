package com.mallaudin.coconut.widget


/**
 * An error aware view. The [Input] returned by
 * this view contains all the information about input validation
 * and error messages.
 *
 * @author Muhammad allaudin
 * Created on 2018-03-24.
 */

interface CoconutView {

    val input: Input?

    /**
     * Set error message on this view
     *
     * @param error - error message
     */
    fun setErrorMessage(error: CharSequence?)

    /**
     * Remove error message from the view
     */
    fun removeError()

    /**
     * Set text watcher on view's [Input] for removing
     * errors.
     *
     * @param inputView input view
     */
    fun setWatcher(inputView: Input?)
}

