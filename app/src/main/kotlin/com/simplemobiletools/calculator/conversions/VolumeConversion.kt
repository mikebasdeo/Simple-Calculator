package com.simplemobiletools.calculator.conversions

class VolumeConversion: Converter() {

    private val mapOfVolumes = mapOf(
            "Litres" to Pair(1.0, "L"),
            "Millilitres" to Pair(1000.0, "mL"),
            "Cubic Feet" to Pair(0.0353147, "ft³"),
            "Cubic Inches" to Pair(61.0237, "in³"),
            "Cubic Metres" to Pair(0.001, "m³")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfVolumes
    }
}