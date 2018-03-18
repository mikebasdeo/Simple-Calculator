package com.simplemobiletools.calculator.conversions

class WeightConversion: Converter() {

    private val mapOfWeights = mapOf(
            "Kilograms" to 1.0,
            "Grams" to 1000.0,
            "Ounces" to 35.274,
            "Pounds" to 2.20462,
            "Stone" to 0.157473
    )

    override fun getMap(): Map<String, Double> {
        return mapOfWeights
    }
}