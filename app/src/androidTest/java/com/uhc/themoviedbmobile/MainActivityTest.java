package com.uhc.themoviedbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.uhc.themoviedbmobile.activity.MainActivity;
import com.uhc.themoviedbmobile.activity.MovieDetailsActivity;
import com.uhc.themoviedbmobile.activity.SettingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by const on 16/12/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    final public IntentsTestRule<MainActivity> activity_rule = new IntentsTestRule<>(MainActivity.class, true,false);

    private Context ctx;

    @Before
    public void setUp() {
        ctx = getInstrumentation().getTargetContext();
        activity_rule.launchActivity(new Intent());
    }

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

    /**
     * Verify if "No conection" message is displayed when it is first time and do not have connection
     */
    @Test
    public void verifyDisplayFirstTimeConnection_MainActivity() throws Throwable {
        activity_rule.runOnUiThread(() -> activity_rule.getActivity().displayFirstTimeNoConnection(true));
        onView(withId(R.id.txv_empty_list))
                .check(matches(withText(ctx.getResources().getString(R.string.no_connection))));
    }

    /**
     * Verify if "No conection" message is NOT displayed when it is NOT first time OR have connection
     */
    @Test
    public void verifyNotDisplayFirstTimeConnection_MainActivity() throws Throwable {
        activity_rule.runOnUiThread(() -> activity_rule.getActivity().displayFirstTimeNoConnection(false));
        onView(withId(R.id.txv_empty_list))
                .check(matches(withText("")));
    }
}
