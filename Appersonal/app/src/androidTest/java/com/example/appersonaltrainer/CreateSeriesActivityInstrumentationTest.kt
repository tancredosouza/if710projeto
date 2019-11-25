package com.example.appersonaltrainer

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.appersonaltrainer.activities.CreateSeriesActivity
import com.example.appersonaltrainer.components.Exercise
import com.example.appersonaltrainer.components.ExerciseType
import com.example.appersonaltrainer.components.Time
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateSeriesActivityInstrumentationTest {
    @get:Rule
    var activityRule: ActivityTestRule<CreateSeriesActivity> =
        ActivityTestRule(CreateSeriesActivity::class.java)

    @Test
    fun whenUserCreatesSeriesWithoutExercise_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userPressesButtonToSaveSeries()

        shouldThrowToastWithMessage("Série deve conter pelo menos um exercício!")
    }

    @Test
    fun whenUserCreatesExerciseWithInvalidTime_shouldNotDisplayInvalidTimes() {
        userCreatesExercise(exerciseWithInvalidTime)

        userPressesButtonToSaveExercise()

        assertIsAddedToSeries(exerciseWithValidTime)
    }

    @Test
    fun whenUserCreatesExercise_shouldSaveToSeries() {
        userCreatesExercise(someExercise)

        userPressesButtonToSaveExercise()

        assertIsAddedToSeries(someExercise)
    }

    @Test
    fun whenUserTriesToCreateNamelessSeries_shouldOnlyDisplayToast() {
        userCreatesExercise(someExercise)

        userPressesButtonToSaveExercise()

        userPressesButtonToSaveSeries()

        shouldThrowToastWithMessage("Nome da série não pode ser vazio!")
    }

    @Test
    fun whenUserTriesToCreateNamelessExercise_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseNameField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage("Nome do exercício não pode estar vazio!")
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutHours_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseHoursField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage("Campo de tempo não pode estar vazio!")
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutMinutes_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseMinutesField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage("Campo de tempo não pode estar vazio!")
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutSeconds_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseSecondsField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage("Campo de tempo não pode estar vazio!")
    }

    private fun userFillsSeriesName() {
        onView(withId(R.id.new_series_name)).perform(typeText(someSeriesName))

        Espresso.closeSoftKeyboard()
    }

    private fun userCreatesExercise(e: Exercise) {
        onView(withId(R.id.new_exercise_button)).perform(typeText(e.name.toString()))
        onView(withId(R.id.hours_new_exercise)).perform(typeText(e.totalTime.hours.toString()))
        onView(withId(R.id.minutes_new_exercise)).perform(typeText(e.totalTime.minutes.toString()))
        onView(withId(R.id.seconds_new_exercise)).perform(typeText(e.totalTime.seconds.toString()))

        Espresso.closeSoftKeyboard()
    }

    private fun userClearsExerciseNameField() {
        clearField(R.id.new_exercise_button)
    }

    private fun userClearsExerciseHoursField() {
        clearField(R.id.hours_new_exercise)
    }

    private fun userClearsExerciseMinutesField() {
        clearField(R.id.minutes_new_exercise)
    }

    private fun userClearsExerciseSecondsField() {
        clearField(R.id.seconds_new_exercise)
    }

    private fun clearField(field: Int) {
        onView(withId(field)).perform(replaceText(""))
    }

    private fun userPressesButtonToSaveSeries() {
        pressButton(R.id.save_new_series_button)
    }

    private fun userPressesButtonToSaveExercise() {
        pressButton(R.id.add_new_exercise_button)
    }

    private fun pressButton(buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }

    private fun shouldThrowToastWithMessage(s: String) {
        ToastMatcher.onToast(s).check(matches(isDisplayed()))
    }

    private fun assertIsAddedToSeries(e: Exercise) {
        assertTrue(
            activityRule.activity.getSeriesToBeCreated().exercises.contains(e)
        )
    }

    companion object {
        val someSeriesName: String = "SOME_NAME"
        val someExerciseType: ExerciseType = ExerciseType.CAMINHADA
        val someExercise: Exercise = Exercise(someExerciseType, Time(10, 40, 50))
        val exerciseWithInvalidTime: Exercise = Exercise(someExerciseType, Time(10, 90, 72))
        val exerciseWithValidTime: Exercise = Exercise(someExerciseType, Time(10, 9, 7))
    }
}
