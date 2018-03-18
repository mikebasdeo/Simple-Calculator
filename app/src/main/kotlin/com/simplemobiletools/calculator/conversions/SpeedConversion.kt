package com.simplemobiletools.calculator.conversions

class SpeedConversion: Converter() {

    private val mapOfSpeeds = mapOf(
            "Km/h" to 1.0,
            "Mph" to 0.621371,
            "M/s" to 0.2777776918389111,
            "Feet/s" to 0.911344,
            "Knots" to 0.539957
    )

    override fun getMap(): Map<String, Double> {
        return mapOfSpeeds
    }
}