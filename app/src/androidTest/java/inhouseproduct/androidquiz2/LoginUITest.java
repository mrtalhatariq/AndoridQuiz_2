package inhouseproduct.androidquiz2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import inhouseproduct.androidquiz2.DB.DbFunctions;
import inhouseproduct.androidquiz2.DB.DbOps;
import inhouseproduct.androidquiz2.DB.models.User;
import inhouseproduct.androidquiz2.fragments.HomeScreenFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginUITest {

    Context appContext;
    private String email,password;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("inhouseproduct.androidquiz2", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void initValidString() {
        // Specify a valid string.
        email = "talha2tbt@hotmail.com";
        password="talha@tt";
    }

    @Test
    public void validateEditText() {

        onView(withId(R.id.etEmail)).perform(typeText(email)).check(matches(withText(email)));
        onView(withId(R.id.etPassword)).perform(typeText(password)).check(matches(withText(password)));


        onView(withId(R.id.btnLogin)).perform(click());






        SystemClock.sleep(1500);


        activityActivityTestRule.getActivity()
                .getFragmentManager().beginTransaction();

        SystemClock.sleep(2000);
    }

    @After
    public void MainPage()
    {


    }



}
