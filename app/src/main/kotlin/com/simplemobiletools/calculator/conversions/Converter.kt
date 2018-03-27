package com.simplemobiletools.calculator.conversions
import java.math.BigDecimal

abstract class Converter {

    open fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double{
        var endingQty: Double = (1.0 / getMap().getValue(beginningUnitType).first)
        endingQty *= getMap().getValue(endingUnitType).first
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return Double.NaN
        return BigDecimal(endingQty).setScale(4, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    abstract fun getMap(): Map<String, Pair<Double, String>>
}