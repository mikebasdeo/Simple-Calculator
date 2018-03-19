package com.simplemobiletools.calculator.conversions

class VolumeConversion: Converter() {

    private val mapOfVolumes = mapOf(
            "Litres" to 1.0,
            "Millilitres" to 1000.0,
            "Cubic Feet" to 0.0353147,
            "Cubic Inches" to 61.0237,
            "Cubic Metres" to 0.001
    )

    override fun getMap(): Map<String, Double> {
        return mapOfVolumes
    }
}