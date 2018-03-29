package com.simplemobiletools.calculator.conversions
import java.math.BigDecimal
import java.text.DecimalFormat

abstract class Converter {

    private var outForm = DecimalFormat("#,###.00")

    open fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): String{
        var endingQty: Double = (1.0 / getMap().getValue(beginningUnitType).first)
        endingQty *= getMap().getValue(endingUnitType).first
        if (beginningQty != null)
            endingQty *= beginningQty
        else
            return ""

        return if (endingQty > 999)
            outForm.format(BigDecimal(endingQty).setScale(4, BigDecimal.ROUND_HALF_UP).toDouble())
        else
            BigDecimal(endingQty).setScale(4, BigDecimal.ROUND_HALF_UP).toDouble().toString()
    }

    abstract fun getMap(): Map<String, Pair<Double, String>>
}