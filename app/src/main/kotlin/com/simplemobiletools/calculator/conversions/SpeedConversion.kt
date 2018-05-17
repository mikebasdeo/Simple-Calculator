package com.simplemobiletools.calculator.conversions

class SpeedConversion: Converter() {

    private val mapOfSpeeds = mapOf(
            "Kilometers per hour" to Pair(1.0, "kph"),
            "Miles per hour" to Pair(0.621371, "mph"),
            "Miles per second" to Pair(0.2777776918389111, "mps"),
            "Feet per second" to Pair(0.911344, "fps"),
            "Knots" to Pair(0.539957, "kn")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfSpeeds
    }
}