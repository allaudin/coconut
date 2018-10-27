package io.github.allaudin

import android.support.design.widget.TextInputLayout
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.view.View
import android.widget.EditText
import io.github.allaudin.coconut.widget.CoconutErrorAwareEt
import io.github.allaudin.coconut.widget.CoconutView
import org.junit.Assert.*


fun editTextErrorHidden(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val editText = castOrThrow(view, EditText::class.java, exception)
        assertNull(editText.error)
    }
}

fun editTextCorrectErrorDisplayed(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val editText = castOrThrow(view, EditText::class.java, exception)
        assertNotNull(editText.error)
        val coconutView = editText as CoconutView
        assertEquals(coconutView.input?.errorMessage, editText.error)
    }
}

fun errorAwareEditTextErrorHidden(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val et = castOrThrow(view, CoconutErrorAwareEt::class.java, exception)
        assertNull(et.editTextView.error)
        assertEquals(View.GONE, et.errorView.visibility)
    }
}

fun errorAwareEditTextCorrectErrorDisplayed(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val et = castOrThrow(view, CoconutErrorAwareEt::class.java, exception)
        assertNotNull(et.errorView.text)
        val coconutView = et as CoconutView
        assertEquals(coconutView.input?.errorMessage, et.errorView.text)
    }
}

fun inputLayoutErrorHidden(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val inputLayout = castOrThrow(view, TextInputLayout::class.java, exception)
        assertFalse(inputLayout.isErrorEnabled)
        assertNull(inputLayout.error)
    }
}


fun inputLayoutCorrectErrorDisplayed(): ViewAssertion {
    return ViewAssertion { view, exception ->
        val layout = castOrThrow(view, TextInputLayout::class.java, exception)
        assertTrue(layout.isErrorEnabled)
        val coconutView = layout as CoconutView
        assertEquals(coconutView.input?.errorMessage, layout.error)
    }
}

fun <T> castOrThrow(view: View, clazz: Class<T>, exception: NoMatchingViewException?): T {
    return try {
        clazz.cast(view)!!
    } catch (castException: ClassCastException) {
        throw exception ?: castException
    }
}
