package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.UnitConversionFragment
import com.simplemobiletools.calculator.conversions.TemperatureConversion
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21])
class UnitConversionActivityTest {

    @Test
    fun temperatureTest(){
        val tc = TemperatureConversion()
        val test = 69.0
        assertEquals(20.56, tc.fahrenheitToCelsius(test),0.01)
        assertEquals(293.7, tc.fahrenheitToKelvin(test),0.01)
        assertEquals(156.2, tc.celsiusToFahrenheit(test),0.01)
        assertEquals(342.15, tc.celsiusToKelvin(test),0.01)
        assertEquals(-204.15, tc.kelvinToCelsius(test), 0.01)
        assertEquals(-335.47, tc.kelvinToFahrenheit(test),0.01)
    }
}