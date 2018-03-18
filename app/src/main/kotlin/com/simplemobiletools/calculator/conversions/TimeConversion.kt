package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class TimeConversion: Converter {
    val mapOfTimes = mapOf(
            "Days" to 1,
            "Hours" to 24,
            "Minutes" to 1440,
            "Seconds" to 86400
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfTimes.getValue(beginningUnitType) as Double)
        endingQty *= mapOfTimes.getValue(endingUnitType) as Double
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }
}