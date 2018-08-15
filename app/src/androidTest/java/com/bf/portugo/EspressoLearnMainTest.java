package com.bf.portugo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.bf.portugo.ui.activity.HomeActivity;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.ui.activity.LearnSamplesActivity;
import com.bf.portugo.ui.activity.LearnVerbActivity;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

import com.vanniktech.espresso.core.utils.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoLearnMainTest {

    //private static final Intent VERBLEARN_ACTIVITY_INTENT = new Intent(getTargetContext(), LearnVerbActivity.class);

    @Rule
    public ActivityTestRule<LearnMainActivity> mActivityTestRule = new ActivityTestRule<>(LearnMainActivity.class);
/*
    public ActivityTestRule<HomeActivity> mHomeActivityTestRule = new ActivityTestRule<>(HomeActivity.class);
    public ActivityTestRule<LearnMainActivity> mActivityTestRule = new ActivityTestRule<>(LearnMainActivity.class);
    public ActivityTestRule<LearnVerbActivity> mLearnVerbActivityTestRule = new ActivityTestRule<>(LearnVerbActivity.class);
*/


    @Before
    public void registerIdlingResource()
    {
        IdlingResource mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);

    }


    @Before
    public void setup(){
        //mMainActivityTestRule.launchActivity(VERBLEARN_ACTIVITY_INTENT);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getTargetContext();
        assertEquals("Incorrect pacckage name","com.bf.portugo", appContext.getPackageName());
    }

    @Test
    public void showVerbLearnActivityForSelection() {
        //Intents.init();

        //onView(withId(R.id.fab_learnverb)).perform(click());

//        ViewInteraction rv = onView(allOf(ViewMatchers.isDisplayed(), withId(R.id.recyclerview_learnmain)));
//        rv.perform(RecyclerViewActions.actionOnItemAtPosition(1, click())).check();

        onView(allOf(ViewMatchers.isDisplayed(), withId(R.id.recyclerview_learnmain)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()))
                .check(matches(CurrentItemMatcher.withCurrentItem()));



        //Intents.release();
    }
/*
    @Test
    public void testSample(){
        if (getRVcount() > 0){
            onView(withId(R.id.our_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

*/

/*
    @Test
    public void showVerbLearnActivityForSelection(){
        Intents.init();

        // two possible recyclerviews on activity
*/
/*
        if (getRVcount() > 0) {
            onView(allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.recyclerview_learnmain)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        }
*//*

//        Intents.init();
//        intended(hasComponent(LearnVerbActivity.class.getName()));
//
//
        //onView(withId(R.id.btn_home_learn)).check(matches((isDisplayed())));
        onView(withId(R.id.fab_learnverb)).perform(click());

//        ))()  allOf(ViewMatchers.isDisplayed(), withId(R.id.recyclerview_learnmain)))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(hasComponent(LearnSamplesActivity.class.getName()));
        //intended(hasComponent(new ComponentName(getTargetContext(), LearnMainActivity.class)));
        Intents.release();
    }
*/

/*
    @Test
    public void onShow_TitleDisplayed() {
        onView(withId(R.id.tv_home_title)).check(matches(withText(R.string.app_name)));
    }
*/

/*
    @Test
    public void showVerbLearnActivityForSelection(){
        Intents.init();

        ViewInteraction rv = onView(allOf(ViewMatchers.isDisplayed(), withId(R.id.recyclerview_learnmain)));
        rv.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        Intents.release();
    }
*/

//    private int getRVcount(){
//        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.our_recycler_view);
//        return recyclerView.getAdapter().getItemCount();
//    }

}
