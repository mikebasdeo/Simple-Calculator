package com.simplemobiletools.calculator.conversions

/**
* Created by Modestos Glykis-Vergados on 2018-03-17.
*/
class LengthConversion:Converter {
    val mapOfLengths = mapOf(
            "Meters" to 1.0,
            "Centimeters" to 100.0,
            "Millimeters" to 1000.0,
            "Kilometers" to 0.001,
            "Feet" to 3.281,
            "Inches" to 39.37,
            "Miles" to 0.0006214,
            "Yards" to 1.093613
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfLengths.getValue(beginningUnitType))
        endingQty *= mapOfLengths.getValue(endingUnitType)
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

    override fun getMap(): Map<String, Double> {
        return mapOfLengths
    }
}