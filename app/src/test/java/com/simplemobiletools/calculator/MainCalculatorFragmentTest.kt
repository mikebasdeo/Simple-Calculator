package com.simplemobiletools.calculator

import android.content.Context
import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.javaluator.ExtendedDoubleEvaluator
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal
import java.math.RoundingMode

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21])
class MainCalculatorFragmentTest {
    private lateinit var activity: MainActivity

    private val evaluator = ExtendedDoubleEvaluator()

    var context = mock(Context::class.java)
    var mockCalc = mock(Calculator::class.java)
    var mockContext = mock(Context::class.java)

    @Before
    fun setUp() {
        //activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun additionTest() {
        val result = evaluator.evaluate("-1.2+3.4")
        assertEquals(2.2, result)
    }

    @Test
    fun subtractionTest() {
        val result = evaluator.evaluate("7.8-2.5")
        assertEquals(5.3, result)
    }

    @Test
    fun multiplyTest() {
        val result = evaluator.evaluate("-3.2*6.6")
        assertEquals(-21.12, result)
    }

    @Test
    fun divisionTest() {
        val result = evaluator.evaluate("4/2")
        assertEquals(2.0, result)
    }

    @Test
    fun moduloTest() {
        val result = evaluator.evaluate("6.5%3")
        assertEquals(0.5, result)
    }

    @Test
    fun powerTest() {
        val result = evaluator.evaluate("3^6")
        assertEquals(729.0, result)
    }

    @Test
    fun bracketsTest() {
        val result = evaluator.evaluate("(3-2)*5")
        assertEquals(5.0, result)
    }

    @Test
    fun equationTest() {
        val result = evaluator.evaluate("(4+3/1)*3+2")
        assertEquals(23.0, result)
    }

    @Test
    fun squareRootTest() {
        val result = evaluator.evaluate("sqrt(9)")
        assertEquals(3.0, result)
    }

    @Test
    fun orderOfOperationsTest() {
        val result = evaluator.evaluate("1+2*(3-1)-1")
        assertEquals(4.0, result)
    }

    @Test
    fun piTest(){
        val result = evaluator.evaluate("pi")
        assertEquals(3.1415, result, 0.001)
    }

    @Test
    fun eTest(){
        val result = evaluator.evaluate("e")
        assertEquals(2.7182, result, 0.001)
    }

    @Test
    fun trigonometryTest(){
        val result = evaluator.evaluate("sin(0)+cos(pi)+tan(pi)")
        assertEquals(-1.0, result, 0.001)
    }

    @Test
    fun logTest(){
        val result = evaluator.evaluate("log(100)")
        assertEquals(2.0, result)
    }

    @Test
    fun lnTest(){
        val result =  evaluator.evaluate("ln(2.7182)")
        assertEquals(1.0, result, 0.001)
    }

    //TODO: Fix loading, test has to read from file. Added local data.json file to use for testing
    //TODO: Failing test needs fixing
    //@Test
   /* fun storageTest() {
        val calc = CalculatorImpl(mockCalc, mockContext)
        calc.displayedNumber = "5.0"
        calc.handleStore("5.0", MEMORY_ONE)
        System.out.println("Loaded: " + calc.displayedNumber)
        calc.handleViewValue(MEMORY_ONE)
        assertEquals("5.0", calc.displayedFormula) //currently loads null
    }

    //TODO: Failing test needs fixing
    //@Test
    fun historyTest() {
        val calc = CalculatorImpl(mockCalc, mockContext)

        calc.setHistoryFile(calc.getFileManager().chooseFileType(FILE, "/History"))
        calc.setResultFile(calc.getFileManager().chooseFileType(FILE, "/Results"))

        calc.handleEquals("2+2")
        val history = calc.getHistoryEntries()
        val results = calc.getResults()
        assert(history.contains("2+2"))
        assert(results.contains("4"))
    }*/

    @Test
    fun arcTrigTest() {
        val result = evaluator.evaluate("asin(0) + acos(0) + atan(0)")
        assertEquals(1.5707963, BigDecimal.valueOf(result).setScale(7, RoundingMode.HALF_UP).toDouble())
    }

    @Test
    fun absTest() {
        val result = evaluator.evaluate("abs(-1)")
        assertEquals(1.0, result)
    }

    @Test
    fun roundTest() {
        val result = evaluator.evaluate("round(3/2)")
        assertEquals(2.0, result)
    }

    @Test
    fun ceilTest() {
        val result = evaluator.evaluate("ceil(3/2)")
        assertEquals(2.0, result)
    }

    @Test
    fun floorTest() {
        val result = evaluator.evaluate("floor(3/2)")
        assertEquals(1.0, result)
    }
}
