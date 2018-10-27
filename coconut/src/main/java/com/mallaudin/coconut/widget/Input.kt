package com.mallaudin.coconut.widget


/**
 * An input field which collects data from the user and
 * contains regex and error messages for validating the input.
 *
 *
 * It must be wrapper in [CoconutView] in order to work with
 * [com.mallaudin.coconut.Coconut]
 *
 * @author Muhammad Allaudin
 * Created on 2018-03-24.
 */

interface Input {

    /**
     * User input
     *
     * @return input as string
     */
    val value: String?


    /**
     * Error message for this input.
     *
     * @return error message
     */
    var errorMessage: String?

    /**
     * Key for delegate validator
     *
     * @return key for delegate validator if specified in xml
     */
    val validatorKey: String?

    /**
     * @return true - if field is optional, false otherwise
     */
    val isOptional: Boolean

    /**
     * Adds text watcher to this view. This text watcher
     * must remove error by call [CoconutView.removeError] from
     * [CoconutTextWatcher.onTextChange]
     *
     * @param watcher text watcher for removing error
     */
    fun setTextWatcher(watcher: CoconutTextWatcher)
}
