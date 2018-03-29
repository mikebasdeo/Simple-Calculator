package com.simplemobiletools.calculator.conversions

class TemperatureConversion: Converter() {

    private val mapOfTemperatures = mapOf(
            "Celsius" to Pair(0.0, "°C"),
            "Fahrenheit" to Pair(32.0, "°F"),
            "Kelvin" to Pair(273.15, "K")
    )

    override fun getMap(): Map<String, Pair<Double, String>> {
        return mapOfTemperatures
    }

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): String {
        if(beginningQty == null)
            return ""
        when (beginningUnitType) {
             "Fahrenheit" -> {
                 return when (endingUnitType) {
                     "Celsius" ->  fahrenheitToCelsius(beginningQty).toString()
                     "Kelvin" -> fahrenheitToKelvin(beginningQty).toString()
                     else -> beginningQty.toString()
                 }
             }
            "Celsius" -> {
                return when (endingUnitType) {
                    "Fahrenheit" -> celsiusToFahrenheit(beginningQty).toString()
                    "Kelvin" -> celsiusToKelvin(beginningQty).toString()
                    else -> beginningQty.toString()
                }
            }
            "Kelvin" -> {
                return when (endingUnitType) {
                    "Fahrenheit" -> kelvinToFahrenheit(beginningQty).toString()
                    "Celsius" -> kelvinToCelsius(beginningQty).toString()
                    else -> beginningQty.toString()
                }
            }
            else -> return ""
         }
    }

    fun fahrenheitToCelsius(beginningQty: Double): Double {
        return (beginningQty - mapOfTemperatures.getValue("Fahrenheit").first) * 5/9
    }

    fun fahrenheitToKelvin(beginningQty: Double): Double {
        return fahrenheitToCelsius(beginningQty) + mapOfTemperatures.getValue("Kelvin").first
    }

    fun celsiusToFahrenheit(beginningQty: Double): Double {
        return (beginningQty * 9/5) + mapOfTemperatures.getValue("Fahrenheit").first
    }

    fun celsiusToKelvin(beginningQty: Double): Double {
        return beginningQty + mapOfTemperatures.getValue("Kelvin").first
    }

    fun kelvinToCelsius(beginningQty: Double): Double {
        return beginningQty - mapOfTemperatures.getValue("Kelvin").first
    }

    fun kelvinToFahrenheit(beginningQty: Double): Double {
        return kelvinToCelsius(beginningQty) * 9/5 + mapOfTemperatures.getValue("Fahrenheit").first
    }
}
