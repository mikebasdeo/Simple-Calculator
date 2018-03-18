package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class SpeedConversion: Converter{
    val mapOfSpeeds = mapOf(
            "Km/h" to 1,
            "Mph" to 0.621371,
            "M/s" to 0.2777776918389111
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfSpeeds.getValue(beginningUnitType)as Double)
        endingQty *= mapOfSpeeds.getValue(endingUnitType) as Double
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

}