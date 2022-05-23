package espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.recipeapp.R;
import com.example.recipeapp.activites.Auth.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Espresso {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

    @Test
    public void loginIsDisplayed() {
        onView(withText("Login")).check(matches(isDisplayed()));
    }
    @Test
    public void ifLoginTextViewIsRight() {
        onView(withId(R.id.login_username)).perform(typeText("rakha"));
        onView(withId(R.id.login_username)).check(matches(withText("rakha")));
    }
    @Test
    public void ifLoginPasswordTextViewIsRight() {
        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.password)).check(matches(withText("123")));
    }
}
