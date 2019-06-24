package com.jccworld.behaviour

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class AddChildInstrumentedTest {
    @get:Rule
    var mActivityRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(DATABASE_NAME)
    }

    @Test
    fun addsChildAndShowsManageChildrenScreen() {
        // init
        onView(withId(R.id.firstName)).perform(clearText(), typeText(A_VALID_FIRST_NAME))

        // run
        onView(withId(R.id.next)).perform(click())

        // verify
        onView(withText(A_VALID_FIRST_NAME)).check(matches(isDisplayed()))
    }

    @Test
    fun cannotAddChildWithNoName() {
        // init
        onView(withId(R.id.firstName)).perform(clearText())

        // run
        onView(withId(R.id.next)).perform(click())

        // verify
        onView(withId(R.id.next)).check(matches(allOf(isDisplayed(), not(isEnabled()))))
    }

    companion object {
        internal const val DATABASE_NAME = "behaviour.db"
        internal const val A_VALID_FIRST_NAME = "Some Text"
    }
}
