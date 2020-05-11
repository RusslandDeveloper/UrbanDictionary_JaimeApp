package com.example.urbandictionaryapp.view

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.urbandictionaryapp.R
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSearchInput(){
        Espresso.onView(ViewMatchers.withId(R.id.button_search))
            .perform(ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(ViewActions.typeText("wasa"), ViewActions.pressImeActionButton())

        Espresso.closeSoftKeyboard()

        Thread.sleep(3000)
    }


}