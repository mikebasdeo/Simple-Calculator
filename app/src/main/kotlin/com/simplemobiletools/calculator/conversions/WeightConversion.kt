package com.simplemobiletools.calculator.conversions

class WeightConversion: Converter() {

    private val mapOfWeights = mapOf(
            "Kilograms" to Pair(1.0, "kg"),
            "Grams" to Pair(1000.0, "g"),
            "Ounces" to Pair(35.274, "oz"),
            "Pounds" to Pair(2.20462, "lb"),
            "Stones" to Pair(0.157473, "st")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfWeights
    }
}