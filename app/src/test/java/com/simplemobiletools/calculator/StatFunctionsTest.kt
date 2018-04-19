package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.helpers.getMean
import com.simplemobiletools.calculator.helpers.getMedian
import com.simplemobiletools.calculator.helpers.getMode
import com.simplemobiletools.calculator.helpers.getRange
import org.junit.Assert.assertEquals
import org.junit.Test

class StatFunctionsTest {

    private val values = arrayListOf("4.3", "3.5", "22.1", "0.0", "4.3", "22.1", "16.7", "13.2", "432.224", "14.2")

    @Test
    fun getMeanTest() {
        assertEquals("53.26", getMean(values))
    }

    @Test
    fun getMedianTest() {
        assertEquals("13.7", getMedian(values))
    }

    @Test
    fun getModeTest() {
        assertEquals("[4.3, 22.1]", getMode(values))
    }

    @Test
    fun getRangeTest() {
        assertEquals("432.224", getRange(values))
    }
}