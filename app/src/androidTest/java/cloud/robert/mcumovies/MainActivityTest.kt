package cloud.robert.mcumovies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import cloud.robert.mcumovies.business.databases.database.McuDatabase
import cloud.robert.mcumovies.views.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var database: McuDatabase

    @Before
    fun setup() {
        hiltRule.inject()
        database.query(
            "INSERT INTO movie(id, genres, title, releaseDate, overview, tagline, posterPath, backdropPath, vote, actors) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            arrayOf(
                0,
                "genre",
                "FakeMovie",
                "2012-01-01",
                "overview",
                "tagline",
                "/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg",
                "/rzRwTcFvttcN1ZpX2xv4j3tSdJu.jpg",
                0.2,
                "0,1"
            )
        )
    }

    @Test
    fun dumbTest() {
        onView(withId(R.id.navigationFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.moviesGrid)).check(matches(isDisplayed()))
    }
}