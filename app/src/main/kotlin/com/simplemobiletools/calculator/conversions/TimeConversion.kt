package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class TimeConversion: Converter {
    val mapOfTimes = mapOf(
            "Days" to 1.0,
            "Hours" to 24.0,
            "Minutes" to 1440.0,
            "Seconds" to 86400.0
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfTimes.getValue(beginningUnitType))
        endingQty *= mapOfTimes.getValue(endingUnitType)
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

    override fun getMap(): Map<String, Double> {
        return mapOfTimes
    }
}