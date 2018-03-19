package com.simplemobiletools.calculator.conversions

abstract class Converter {

    open fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double{
        var endingQty: Double = (1.0 / getMap().getValue(beginningUnitType))
        endingQty *= getMap().getValue(endingUnitType)
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return endingQty
    }

    abstract fun getMap(): Map<String, Double>
}