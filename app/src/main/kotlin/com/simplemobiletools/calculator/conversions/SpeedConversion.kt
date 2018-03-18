package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class SpeedConversion: Converter{
    val mapOfSpeeds = mapOf(
            "Km/h" to 1.0,
            "Mph" to 0.621371,
            "M/s" to 0.2777776918389111,
            "Feet/s" to 0.911344,
            "Knots" to 0.539957
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfSpeeds.getValue(beginningUnitType))
        endingQty *= mapOfSpeeds.getValue(endingUnitType)
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

    override fun getMap(): Map<String, Double> {
        return mapOfSpeeds
    }
}