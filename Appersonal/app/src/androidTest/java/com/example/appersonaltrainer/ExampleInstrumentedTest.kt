package com.example.appersonaltrainer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun whenActionButtonGetsCreated_shouldBeClicked() {
        clickActionButton()
    }

    private fun clickActionButton() {
        val actionButton = fetchActionButton()

        actionButton.perform(click())
    }

    private fun fetchActionButton(): ViewInteraction {
        val actionButtonView = withId(R.id.action_button)

        return onView(actionButtonView)
    }
}
