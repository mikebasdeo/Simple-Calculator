package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class WeightConversion:Converter {

    val mapOfWeights = mapOf(
            "Kilograms" to 1,
            "Grams" to 1000,
            "Ounces" to 35.274,
            "Pounds" to 2.20462,
            "Stone" to 0.157473
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfWeights.getValue(beginningUnitType) as Double)
        endingQty *= mapOfWeights.getValue(endingUnitType) as Double
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }
}