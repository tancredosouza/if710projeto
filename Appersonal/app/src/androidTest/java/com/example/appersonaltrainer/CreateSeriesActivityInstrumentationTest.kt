package com.example.appersonaltrainer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.appersonaltrainer.activities.CreateSeriesActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateSeriesActivityInstrumentationTest {
    @get:Rule
    var activityRule: ActivityTestRule<CreateSeriesActivity> =
        ActivityTestRule(CreateSeriesActivity::class.java)

    @Test
    fun whenUserTriesToCreateNamelessSeries_shouldOnlyDisplayToast() {
        onView(withId(R.id.new_series_name)).perform(typeText(""))

        onView(withId(R.id.save_new_series_button)).perform(click())

        ToastMatcher.onToast("Nome da série não pode ser vazio!").check(matches(isDisplayed()))
    }
}
