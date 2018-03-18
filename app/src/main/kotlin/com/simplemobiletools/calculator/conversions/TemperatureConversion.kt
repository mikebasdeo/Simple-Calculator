//package com.simplemobiletools.calculator.conversions
//
///**
// * Created by George on 3/17/2018.
// */
//class TemperatureConversion:Converter {
//
//    override fun calculate(beginningQty: Double?, beginningUnitType: String, endingUnitType: String): Double {
//
//        when (beginningUnitType) {
//             "Farenheit" -> {
//                 //if f to c
//                 if (endingUnitType == "Celsius"){
//                     return (beginningQty as Double -32)*5/9
//                 }
//                 //if f to k
//                 if (endingUnitType == "Kelvin"){
//                     return (beginningQty as Double + 459.67)*5/9
//                 }
//                 //else f to f
//                 return beginningQty as Double
//             }
//
//             "Celsius" -> {
//                 //if c to f *9/5 + 32
//                 if (endingUnitType == "Farenheit"){
//                     return (beginningQty as Double *9/5)+32
//                 }
//                 //if c to k
//                 if (endingUnitType == "Kelvin"){
//                     return beginningQty as Double + 273.15
//                 }
//                 //else c to c
//                 return beginningQty as Double
//             }
//
//             "Kelvin" -> {
//                 //if k to c
//                 if (endingUnitType == "Celsius"){
//                     return beginningQty as Double - 273.15
//                 }
//                 //if k to f
//                 if (endingUnitType == "Farenheit"){
//                     return (beginningQty as Double * 9/5) - 459.67
//                 }
//                 //else k to k
//                 return beginningQty as Double
//             }
//         }
//        //kotlin requires me to have a return outside the when statement, so:
//        return 0.0
//    }
//}