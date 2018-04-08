package com.simplemobiletools.calculator.export

import java.io.Writer
import java.io.IOException


class ExportManager {

    private val DEFAULTSEPARATOR = ", "

    @Throws(IOException::class)
    fun writeLine(w: Writer, values: List<String>) {

        val separators = DEFAULTSEPARATOR
        var first = true

        val sb = StringBuilder()
        for (value in values) {

            if (!first) {
                sb.append(separators)
                sb.append(value)
            } else {
                sb.append(value)
            }

            first = false
        }
        sb.append("\n")
        w.append(sb.toString())
    }
}