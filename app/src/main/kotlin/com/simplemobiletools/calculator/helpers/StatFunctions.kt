package com.simplemobiletools.calculator.helpers

fun updateStats(results: ArrayList<String>){
    getMean(results)
    getMedian(results)
    getMode(results)
    getRange(results)
}

fun getMean(results: ArrayList<String>): String{
    var avg = 0.0
    for(r in results){
        avg += r.toDouble()
    }
    return (avg / results.size).toString()
}

fun getMedian(results: ArrayList<String>): String{
    results.sort()
    return if(results.size % 2 == 0)
        ((results[results.size/2].toDouble() + results[results.size/2 - 1].toDouble())/2).toString()
    else
        (results[results.size/2].toDouble()).toString()
}

fun getMode(results: ArrayList<String>): String{
    if(results.isEmpty())
        return ""

    val storeValues = hashMapOf<String, Int>()
    val listOfModes = mutableListOf<String>()
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
    listOfModes.sort()
    return listOfModes.toString()
}

fun getRange(results: ArrayList<String>): String {
    results.sort()
    return "${results.first()}, ${results.last()}"
}
