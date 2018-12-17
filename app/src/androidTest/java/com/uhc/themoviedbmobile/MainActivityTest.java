package com.uhc.themoviedbmobile;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.uhc.themoviedbmobile.activity.MainActivity;
import com.uhc.themoviedbmobile.activity.MovieDetailsActivity;
import com.uhc.themoviedbmobile.activity.SettingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by const on 16/12/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> activity_rule = new IntentsTestRule<>(MainActivity.class);

    /**
     * Verify if when a list item is clicked the activity MovieDetailsActivity is opened
     */
    @Test
    public void verifyListItemClick_MainActivity() {
        onView(withId(R.id.rv_movies))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(MovieDetailsActivity.class.getName()));
    }

    /**
     * Verify if when the setting item on toolbar is clicked the activity SettingActivity is opened
     */
    @Test
    public void verifySettingsClick_MainActivity() {
        onView(withId(R.id.action_settings))
                .perform(click());
        intended(hasComponent(SettingActivity.class.getName()));
    }
}
