package com.simplemobiletools.calculator.conversions

class TimeConversion: Converter() {

    private val mapOfTimes = mapOf(
            "Days" to 1.0,
            "Hours" to 24.0,
            "Minutes" to 1440.0,
            "Seconds" to 86400.0,
            "Weeks" to 0.142857,
            "Months" to 0.0328767,
            "Years" to 0.00273973
    )

    override fun getMap(): Map<String, Double> {
        return mapOfTimes
    }
}