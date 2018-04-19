package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.BinaryCalculatorActivity;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BinaryCalculatorFragmentUITest extends TestCase {
    @Rule public final ActivityTestRule<BinaryCalculatorActivity> activity = new ActivityTestRule<>(BinaryCalculatorActivity.class);

@Test
public void AddTest(){

    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_plus);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_1);
    press(R.id.btn_0);
    press(R.id.btn_0);

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
        press(R.id.btn_minus);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

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
        press(R.id.btn_multiply);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);

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
        press(R.id.btn_divide);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);

        checkResult("101111");
    }

    //TODO: Figure our why these tests fail on CI build, despite passing locally
    //@Test
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
        press(R.id.btn_all_clear);
        checkResult("");
        checkBinary1("");
        checkBinary2("");
    }

    //TODO: Figure our why these tests fail on CI build, despite passing locally
    //@Test
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
        checkBinary1("1000011111");
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_del);
        checkBinary2("10");
    }

    @Test
    public void ConvertTest(){
        press(R.id.btn_1);
        press(R.id.btn_1);
        press(R.id.btn_1);
     checkResult("7");
    }

    @Test
    public void andTest(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_and);
        checkResult("1000");
    }
    @Test
    public void orTest(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_or);
        checkResult("1011");
    }

    @Test
    public void xor_test(){
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.binary_number_2);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_or);
        checkResult("11");
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

    private void checkBinary1(String desired) {
        onView(withId(R.id.binary_number_1)).check(matches(withText(desired)));
    }

    private void checkBinary2(String desired) {
        onView(withId(R.id.binary_number_2)).check(matches(withText(desired)));
    }
}
