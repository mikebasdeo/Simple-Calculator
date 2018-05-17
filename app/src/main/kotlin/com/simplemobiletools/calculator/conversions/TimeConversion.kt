package com.simplemobiletools.calculator.conversions

class TimeConversion: Converter() {

    private val mapOfTimes = mapOf(
            "Days" to Pair(1.0, "d"),
            "Hours" to Pair(24.0, "h"),
            "Minutes" to Pair(1440.0, "min"),
            "Seconds" to Pair(86400.0, "s"),
            "Weeks" to Pair(0.142857, "wk"),
            "Months" to Pair(0.0328767, "mo"),
            "Years" to Pair(0.00273973, "yr")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfTimes
    }
}