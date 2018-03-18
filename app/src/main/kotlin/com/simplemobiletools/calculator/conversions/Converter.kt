package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
interface Converter {
    fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double

}