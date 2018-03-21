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

    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
        if(beginningQty == null)
            return Double.NaN
        when (beginningUnitType) {
             "Fahrenheit" -> {
                 return when (endingUnitType) {
                     "Celsius" ->  fahrenheitToCelsius(beginningQty)
                     "Kelvin" -> fahrenheitToKelvin(beginningQty)
                     else -> beginningQty
                 }
             }
            "Celsius" -> {
                return when (endingUnitType) {
                    "Fahrenheit" -> celsiusToFahrenheit(beginningQty)
                    "Kelvin" -> celsiusToKelvin(beginningQty)
                    else -> beginningQty
                }
            }
            "Kelvin" -> {
                return when (endingUnitType) {
                    "Fahrenheit" -> kelvinToFahrenheit(beginningQty)
                    "Celsius" -> kelvinToCelsius(beginningQty)
                    else -> beginningQty
                }
            }
            else -> return Double.NaN
         }
    }

    private fun fahrenheitToCelsius(beginningQty: Double): Double {
        return (beginningQty - mapOfTemperatures.getValue("Fahrenheit").first) * 5/9
    }

    private fun fahrenheitToKelvin(beginningQty: Double): Double {
        return fahrenheitToCelsius(beginningQty) + mapOfTemperatures.getValue("Kelvin").first
    }

    private fun celsiusToFahrenheit(beginningQty: Double): Double {
        return (beginningQty * 9/5) + mapOfTemperatures.getValue("Fahrenheit").first
    }

    private fun celsiusToKelvin(beginningQty: Double): Double {
        return beginningQty + mapOfTemperatures.getValue("Kelvin").first
    }

    private fun kelvinToCelsius(beginningQty: Double): Double {
        return beginningQty - mapOfTemperatures.getValue("Kelvin").first
    }

    private fun kelvinToFahrenheit(beginningQty: Double): Double {
        return kelvinToCelsius(beginningQty) * 9/5 + mapOfTemperatures.getValue("Fahrenheit").first
    }
}
