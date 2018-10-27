package com.mallaudin.demo

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withHint
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun error_Message_Is_Not_Displayed_Until_Data_Is_Validated() {
        onView(withId(R.id.email_layout))
                .check(inputLayoutErrorHidden())

        onView(withId(R.id.non_empty_et))
                .check(editTextErrorHidden())

        onView(withId(R.id.digits_only))
                .check(errorAwareEditTextErrorHidden())

    }

    @Test
    fun error_Message_Is_Displayed_When_Validated_Against_Invalid_Input() {

        clickSubmit()

        onView(withId(R.id.email_layout))
                .check(inputLayoutCorrectErrorDisplayed())

        onView(withId(R.id.non_empty_et))
                .check(editTextCorrectErrorDisplayed())

        onView(withId(R.id.digits_only))
                .check(errorAwareEditTextCorrectErrorDisplayed())
    }

    @Test
    fun error_Message_Is_Removed_After_User_Types_Text() {

        clickSubmit()

        onView(withId(R.id.email))
                .perform(typeText("foo"),
                        closeSoftKeyboard())

        onView(withId(R.id.non_empty_et))
                .perform(typeText("  "),
                        closeSoftKeyboard())

        onView(withHint(R.string.enter_digits_only))
                .perform(typeText("123"),
                        closeSoftKeyboard())


        onView(withId(R.id.email_layout)).check(inputLayoutErrorHidden())

        onView(withId(R.id.non_empty_et)).check(editTextErrorHidden())

        onView(withId(R.id.digits_only)).check(errorAwareEditTextErrorHidden())
    }

    @Test
    fun error_Is_Not_Displayed_For_Valid_Input() {

        onView(withId(R.id.non_empty_et))
                .perform(typeText("a"),
                        closeSoftKeyboard())

        onView(withId(R.id.email))
                .perform(typeText("ali@gmail.com"),
                        closeSoftKeyboard())


        onView(ViewMatchers.withHint(R.string.enter_digits_only))
                .perform(typeText("1234567"),
                        closeSoftKeyboard())

        clickSubmit()

        onView(withId(R.id.email_layout)).check(inputLayoutErrorHidden())

        onView(withId(R.id.non_empty_et)).check(editTextErrorHidden())

        onView(withId(R.id.digits_only)).check(errorAwareEditTextErrorHidden())
    }


    private fun clickSubmit() {
        onView(withId(R.id.submit)).perform(click())
    }
}