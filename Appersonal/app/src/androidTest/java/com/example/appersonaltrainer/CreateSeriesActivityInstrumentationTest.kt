package com.example.appersonaltrainer

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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

        shouldThrowToastWithMessage(R.string.series_has_no_exercise_error)
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
    fun whenUserCreatesAndSavesMultipleExercises_shouldSaveToSeries() {
        userCreatesAndSavesMultipleExercises(sampleExercises)

        assertAllExercisesWereAdded(expectedExercises)
    }

    @Test
    fun whenUserTriesToCreateNamelessSeries_shouldOnlyDisplayToast() {
        userCreatesExercise(someExercise)

        userPressesButtonToSaveExercise()

        userPressesButtonToSaveSeries()

        shouldThrowToastWithMessage(R.string.series_has_no_name_error)
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutHours_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseHoursField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage(R.string.exercise_has_empty_time_field_error)
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutMinutes_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseMinutesField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage(R.string.exercise_has_empty_time_field_error)
    }

    @Test
    fun whenUserTriesToCreateExerciseWithoutSeconds_shouldOnlyDisplayToast() {
        userFillsSeriesName()

        userCreatesExercise(someExercise)

        userClearsExerciseSecondsField()

        userPressesButtonToSaveExercise()

        shouldThrowToastWithMessage(R.string.exercise_has_empty_time_field_error)
    }

    private fun userFillsSeriesName() {
        onView(withId(R.id.new_series_name)).perform(typeText(seriesName))

        Espresso.closeSoftKeyboard()
    }

    private fun userCreatesExercise(e: Exercise) {
        userChoosesExerciseType(e.type)

        userFillsTimeFields(e)

        Espresso.closeSoftKeyboard()
    }

    private fun userChoosesExerciseType(exerciseType: ExerciseType) {
        pressButton(R.id.new_exercise_button)

        userClicksOnPopUpMenu(exerciseType)
    }

    private fun userClicksOnPopUpMenu(exerciseType: ExerciseType) {
        pressPopupItem(exerciseType.toString())
    }

    private fun userFillsTimeFields(e: Exercise) {
        onView(withId(R.id.hours_new_exercise)).perform(typeText(e.totalTime.hours.toString()))
        onView(withId(R.id.minutes_new_exercise)).perform(typeText(e.totalTime.minutes.toString()))
        onView(withId(R.id.seconds_new_exercise)).perform(typeText(e.totalTime.seconds.toString()))
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

    private fun userPressesButtonToSaveExercise() {
        pressButton(R.id.add_new_exercise_button)
    }

    private fun userPressesButtonToSaveSeries() {
        pressButton(R.id.save_new_series_button)
    }

    private fun pressButton(buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }

    private fun pressPopupItem(text: String) {
        onView(withText(text)).inRoot(isPlatformPopup()).perform(click())
    }

    private fun shouldThrowToastWithMessage(messageId: Int) {
        ToastMatcher.onToast(messageId).check(matches(isDisplayed()))
    }

    private fun userCreatesAndSavesMultipleExercises(listOfExercises: List<Exercise>) {
        for (exercise in listOfExercises) {
            userCreatesExercise(exercise)
            userPressesButtonToSaveExercise()
        }
    }

    private fun assertAllExercisesWereAdded(expected: List<Exercise>) {
        for (exercise in expected) {
            assertIsAddedToSeries(exercise)
        }
    }

    private fun assertIsAddedToSeries(e: Exercise) {
        assertTrue(
            activityRule.activity.getSeriesToBeCreated().exercises.contains(e)
        )
    }

    companion object {
        const val seriesName: String = "SERIES_NAME"

        val someExercise = Exercise(ExerciseType.CAMINHADA, Time(10, 40, 50))

        val exerciseWithInvalidTime = Exercise(ExerciseType.CICLISMO, Time(10, 90, 72))
        val exerciseWithValidTime = Exercise(ExerciseType.CICLISMO, Time(10, 9, 7))

        val sampleExercises =
            listOf(
                Exercise(ExerciseType.CAMINHADA, Time(1, 40, 59)),
                Exercise(ExerciseType.YOGA, Time(0, 10, 40)),
                Exercise(ExerciseType.CORRIDA, Time(0, 50, 0)),
                Exercise(ExerciseType.CICLISMO, Time(0, 30, 0)),
                exerciseWithInvalidTime
            )

        val expectedExercises =
            listOf(
                Exercise(ExerciseType.CAMINHADA, Time(1, 40, 59)),
                Exercise(ExerciseType.YOGA, Time(0, 10, 40)),
                Exercise(ExerciseType.CORRIDA, Time(0, 50, 0)),
                Exercise(ExerciseType.CICLISMO, Time(0, 30, 0)),
                exerciseWithValidTime
            )
    }
}
