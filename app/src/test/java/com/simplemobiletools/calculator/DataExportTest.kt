package com.simplemobiletools.calculator

import android.content.Context
import com.simplemobiletools.calculator.activities.HistoryActivity
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.javaluator.ExtendedDoubleEvaluator
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21])
class DataExportTest {

    @Test
    fun exportTest() {
        val mockHistory = Mockito.mock(HistoryActivity::class.java)
        mockHistory.exportData()
        verify(mockHistory).exportData()
    }
}
