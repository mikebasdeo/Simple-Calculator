package com.simplemobiletools.calculator.export

import com.opencsv.CSVWriter

class ExportManager {

    fun writeLine(w: CSVWriter, values: List<String>) {
        val sb = values.toTypedArray()
        w.writeNext(sb)
    }
}