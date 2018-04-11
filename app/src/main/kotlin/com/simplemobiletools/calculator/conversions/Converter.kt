package com.simplemobiletools.calculator.conversions

abstract class Converter {
    open fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double{
        var endingQty: Double = (1.0 / getMap().getValue(beginningUnitType).first)
        endingQty *= getMap().getValue(endingUnitType).first
        return if (beginningQty != null) {
            endingQty *= beginningQty
            endingQty
        }
        else
            0.0
    }

    abstract fun getMap(): Map<String, Pair<Double, String>>
}