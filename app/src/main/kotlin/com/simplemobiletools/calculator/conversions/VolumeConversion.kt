package com.simplemobiletools.calculator.conversions

class VolumeConversion: Converter() {

    private val mapOfVolumes = mapOf(
            "Litres" to 1.0,
            "Millilitres" to 1000.0,
            "Cubic feet" to 0.0353147,
            "Cubic inches" to 61.0237,
            "Cubic metres" to 0.001
    )

    override fun getMap(): Map<String, Double> {
        return mapOfVolumes
    }
}