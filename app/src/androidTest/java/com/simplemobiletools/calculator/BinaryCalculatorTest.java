package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.BinaryCalculatorActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class BinaryCalculatorTest {
    @Rule public final ActivityTestRule<BinaryCalculatorActivity> activity = new ActivityTestRule<>(BinaryCalculatorActivity.class);

@Test
public void AddTest(){

    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.binary_number_2);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_0);
    press(R.id.btn_plus);
    checkResult("101010");
}

@Test
public void SubtractionTest(){
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.binary_number_2);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_0);
    press(R.id.btn_minus);
    checkResult("1010011");

}

@Test
public void MultiplicationTest(){
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.binary_number_2);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_0);
    press(R.id.btn_multiply);
    checkResult("110000100100");
}

    @Test
    public void DivisionTest(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_divide);
        checkResult("101111");
    }

    @Test
    public void ClearScreenTest(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_divide);
        longPress(R.id.btn_all_clear);
        checkResult("");
        onView(withId(R.id.binary_number_1)).check(matches(withText("")));
        onView(withId(R.id.binary_number_2)).check(matches(withText("")));

    }

    @Test
    public void DeleteTest(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_del);
        onView(withId(R.id.binary_number_1)).check(matches(withText("1000011111")));
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_del);
        onView(withId(R.id.binary_number_2)).check(matches(withText("10")));
    }

    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.binary_result)).check(matches(withText(desired)));
    }

    private void checkFormula(String desired) {
        onView(withId(R.id.formula)).check(matches(withText(desired)));
    }
}
