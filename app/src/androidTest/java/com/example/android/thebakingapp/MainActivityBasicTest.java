package com.example.android.thebakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Arjun Vidyarthi on 20-Mar-18.
 */
@RunWith(AndroidJUnit4.class)

public class MainActivityBasicTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkRecyclerView() {
        Espresso.onView(withId(R.id.recycler_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        Espresso.onView(withId(R.id.steps_list)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.ing_list)).check(matches(ViewMatchers.isDisplayed()));

    }
}
