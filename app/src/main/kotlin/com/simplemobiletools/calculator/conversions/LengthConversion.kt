package com.simplemobiletools.calculator.conversions

class LengthConversion: Converter() {

    private val mapOfLengths = mapOf(
            "Meters" to 1.0,
            "Centimeters" to 100.0,
            "Millimeters" to 1000.0,
            "Kilometers" to 0.001,
            "Feet" to 3.281,
            "Inches" to 39.37,
            "Miles" to 0.0006214,
            "Yards" to 1.093613
    )

    override fun getMap(): Map<String, Double> {
        return mapOfLengths
    }
}