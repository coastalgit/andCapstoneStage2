package com.bf.portugo;

import android.content.Context;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bf.portugo.ui.activity.LearnMainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("CanBeFinal")
@RunWith(AndroidJUnit4.class)
public class EspressoLearnMainTest {

    @Rule
    public ActivityTestRule<LearnMainActivity> mActivityTestRule = new ActivityTestRule<>(LearnMainActivity.class);

    @Before
    public void registerIdlingResource()
    {
        IdlingResource mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);

    }

    @Test
    public void useAppContext() {
        Context appContext = getTargetContext();
        assertEquals("Incorrect pacckage name","com.bf.portugo", appContext.getPackageName());
    }

    @Test
    public void selectVerbFromRecyclerView() {

        onView(allOf(ViewMatchers.isDisplayed(), withId(R.id.recyclerview_learnmain)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()))
                .check(matches(isDisplayed()));

    }

}
