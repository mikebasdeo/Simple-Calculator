package com.simplemobiletools.calculator.conversions

class LengthConversion: Converter() {

    private val mapOfLengths = mapOf(
            "Meters" to Pair(1.0, "m"),
            "Centimeters" to Pair(100.0, "cm"),
            "Millimeters" to Pair(1000.0, "mm"),
            "Kilometers" to Pair(0.001, "km"),
            "Feet" to Pair(3.281, "ft"),
            "Inches" to Pair(39.37, "in"),
            "Miles" to Pair(0.0006214, "mi"),
            "Yards" to Pair(1.093613, "yd")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfLengths
    }
}