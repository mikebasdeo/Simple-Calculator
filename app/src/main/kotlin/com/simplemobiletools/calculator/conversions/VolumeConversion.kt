package com.simplemobiletools.calculator.conversions

/**
 * Created by George on 3/17/2018.
 */
class VolumeConversion:Converter {

    val mapOfVolumes = mapOf(
            "Litres" to 1,
            "Millilitres" to 1000
    )

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        var endingQty: Double = (1.0 / mapOfVolumes.getValue(beginningUnitType) as Double)
        endingQty *= mapOfVolumes.getValue(endingUnitType) as Double
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }
}