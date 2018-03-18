package com.simplemobiletools.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.simplemobiletools.calculator.activities.MainActivity;
import com.simplemobiletools.calculator.activities.UnitConversionActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class UnitConversionTest {
    @Rule public final ActivityTestRule<UnitConversionActivity> activity = new ActivityTestRule<>(UnitConversionActivity.class);

    @Test
    public void testDistanceConvert() {
        press(R.id.conversion_type_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Distance"))).perform(click());
        press(R.id.units_before_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Kilometers"))).perform(click());
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("1000.0");
    }

    @Test
    public void testTimeConvert() {
        press(R.id.conversion_type_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Time"))).perform(click());
        press(R.id.units_before_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Minutes"))).perform(click());
        press(R.id.units_after_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Seconds"))).perform(click());
        press(R.id.btn_1);
        press(R.id.btn_2);
        press(R.id.btn_equals);
        checkResult("720.0");
    }

    @Test
    public void testWeightConvert() {
        press(R.id.conversion_type_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Weight"))).perform(click());
        press(R.id.units_before_spinner);
        onData(allOf(is(instanceOf(String.class)),is("Pounds"))).perform(click());
        press(R.id.units_after_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Ounces"))).perform(click());
        press(R.id.btn_1);
        press(R.id.btn_equals);
        checkResult("16.000036287432756");
    }

    @Test
    public void testSpeedConvert(){
        press(R.id.conversion_type_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Speed"))).perform(click());
        press(R.id.units_before_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Mph"))).perform(click());
        press(R.id.units_after_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Km/h"))).perform(click());
        press(R.id.btn_8);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("128.74755983140506");
    }

    @Test
    public void testVolumeConvert(){
        press(R.id.conversion_type_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Volume"))).perform(click());
        press(R.id.units_before_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Cubic inches"))).perform(click());
        press(R.id.units_after_spinner);
        onData(allOf(is(instanceOf(String.class)), is("Litres"))).perform(click());
        press(R.id.btn_1);
        press(R.id.btn_0);
        press(R.id.btn_0);
        press(R.id.btn_equals);
        checkResult("1.6387075841025702");
    }
    private void press(int id) {
        onView(withId(id)).perform(click());
    }

    private void longPress(int id) {
        onView(withId(id)).perform(longClick());
    }

    private void checkResult(String desired) {
        onView(withId(R.id.after)).check(matches(withText(desired)));
    }

    private void checkFormula(String desired) {
        onView(withId(R.id.formula)).check(matches(withText(desired)));
    }
}
