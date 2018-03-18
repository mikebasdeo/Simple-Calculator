package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class WeightConversion:Converter {

    val mapOfWeights = mapOf(
            "Kilograms" to 1.0,
            "Grams" to 1000.0,
            "Ounces" to 35.274,
            "Pounds" to 2.20462,
            "Stone" to 0.157473
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfWeights.getValue(beginningUnitType))
        endingQty *= mapOfWeights.getValue(endingUnitType)
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

    override fun getMap(): Map<String, Double> {
        return mapOfWeights
    }
}