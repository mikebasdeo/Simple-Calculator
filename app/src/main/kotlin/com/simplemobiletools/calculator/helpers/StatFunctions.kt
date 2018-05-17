package com.simplemobiletools.calculator.helpers

fun updateStats(results: ArrayList<String>){
    getMean(results)
    getMedian(results)
    getMode(results)
    getRange(results)
}

fun getMean(results: ArrayList<String>): String{
    var avg = 0.0
    for(r in results) {
        if(r.isEmpty())
            return ""
        avg += r.replace(",","").toDouble()
    }

    avg /= results.size

    return String.format(java.util.Locale.US,"%.2f", avg)
}

fun getMedian(results: ArrayList<String>): String{
    for(r in results) {
        if (r.isEmpty())
            return ""
    }
    val listOfSortedResults = sortListOfStringsOfDoubles(results)
    return if(listOfSortedResults.size % 2 == 0) {
        ((listOfSortedResults[listOfSortedResults.size / 2] + listOfSortedResults[listOfSortedResults.size / 2 - 1]) / 2).toString()
    } else {
        (listOfSortedResults[listOfSortedResults.size / 2]).toString()
    }

}

fun getMode(results: ArrayList<String>): String{
    for(r in results) {
        if (r.isEmpty())
            return ""
    }
    val storeValues = hashMapOf<String, Int>()
    val listOfModes = arrayListOf<String>()
    var highestCount = 0

    //Get count of each occurrence
    for(r in results){
        if(storeValues[r] == null)
            storeValues[r] = 1
        else{
            val count = storeValues[r]
            storeValues[r] = count!! + 1
        }
    }
    //Store highest count
    for(s in storeValues){
        if(highestCount < s.value)
            highestCount = s.value
    }
    //Every number with an equal highest count is added to return list
    for(s in storeValues){
        if(s.value == highestCount)
            listOfModes.add(s.key)
    }
    val listOfSortedModes = sortListOfStringsOfDoubles(listOfModes)
    return listOfSortedModes.toString()
}

fun getRange(results: ArrayList<String>): String {
    for(r in results) {
        if (r.isEmpty())
            return ""
    }
    val listOfSortedModes = sortListOfStringsOfDoubles(results)
    return (listOfSortedModes.last() - listOfSortedModes.first()).toString()
}

private fun sortListOfStringsOfDoubles(listOfStringOfDoubles: ArrayList<String>): ArrayList<Double> {
    val listOfDoubles = ArrayList<Double>()
    for(l in listOfStringOfDoubles){
        listOfDoubles.add(l.replace(",","").toDouble())
    }
    listOfDoubles.sort()
    return listOfDoubles
}
