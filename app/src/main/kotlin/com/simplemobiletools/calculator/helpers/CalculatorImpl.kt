package com.simplemobiletools.calculator.helpers
import android.content.Context
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.CONSTANT.ABSOLUTE_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCCOS
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCSINE
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCTANGENT
import com.simplemobiletools.calculator.helpers.CONSTANT.CEILING
import com.simplemobiletools.calculator.helpers.CONSTANT.COSINE
import com.simplemobiletools.calculator.helpers.CONSTANT.CUBE
import com.simplemobiletools.calculator.helpers.CONSTANT.DIGIT
import com.simplemobiletools.calculator.helpers.CONSTANT.DIVIDE
import com.simplemobiletools.calculator.helpers.CONSTANT.E
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_EMPTY_RESULT
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_READ_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.ERROR_SAVE_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.FILE
import com.simplemobiletools.calculator.helpers.CONSTANT.FLOOR
import com.simplemobiletools.calculator.helpers.CONSTANT.LEFT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_ONE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_THREE
import com.simplemobiletools.calculator.helpers.CONSTANT.MEMORY_TWO
import com.simplemobiletools.calculator.helpers.CONSTANT.MINUS
import com.simplemobiletools.calculator.helpers.CONSTANT.MODULO
import com.simplemobiletools.calculator.helpers.CONSTANT.MULTIPLY
import com.simplemobiletools.calculator.helpers.CONSTANT.NATURAL_LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.NEGATION
import com.simplemobiletools.calculator.helpers.CONSTANT.PI
import com.simplemobiletools.calculator.helpers.CONSTANT.PLUS
import com.simplemobiletools.calculator.helpers.CONSTANT.POWER
import com.simplemobiletools.calculator.helpers.CONSTANT.RANDOM
import com.simplemobiletools.calculator.helpers.CONSTANT.RECIPROCAL
import com.simplemobiletools.calculator.helpers.CONSTANT.RIGHT_BRACKET
import com.simplemobiletools.calculator.helpers.CONSTANT.ROOT
import com.simplemobiletools.calculator.helpers.CONSTANT.ROUNDING
import com.simplemobiletools.calculator.helpers.CONSTANT.SINE
import com.simplemobiletools.calculator.helpers.CONSTANT.SQUARE
import com.simplemobiletools.calculator.helpers.CONSTANT.TANGENT
import com.simplemobiletools.calculator.helpers.CONSTANT.TEMP_FILE
import com.simplemobiletools.calculator.javaluator.ExtendedDoubleEvaluator
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class CalculatorImpl(calculator: Calculator, private val context: Context) {
    var displayedFormula: String
    var displayedNumber: String
    //var lastKey: String
    private var canUseDecimal: Boolean
    private var mCallback: Calculator? = calculator
    private var mSavedValue1: File
    private var mSavedValue2: File
    private var mSavedValue3: File
    private var mEquationHistory: File
    private var mResultHistory: File
    private var fileManager: FileHandler = FileHandler(context)

    //If any listOfSpecialLastEntries precedes listOfSpecialOperations, automatically add a * in between them. 4pi = 4*pi.
    //See implementation in fun handleOperationOnFormula(operation: String)
    private val listOfSpecialLastEntries = listOf(DIGIT, PI, E, RIGHT_BRACKET, SQUARE, CUBE)
    private val listOfSpecialOperations = listOf(LEFT_BRACKET, PI, E, SINE, COSINE,  TANGENT,
                                                    LOGARITHM, NATURAL_LOGARITHM, ROOT, ARCSINE,
                                                    ARCCOS, ARCTANGENT, ROUNDING, CEILING, FLOOR)

    //Every time a digit or operation is entered, we keep track of the length. In this way, when we
    //delete digits or operations, our program will automatically delete the appropriate amount of
    //characters in the formula string. Example: sin(90) would delete in the following order: ) ->
    //0 -> 9 -> sin( ... This prevents user's from deleting an operation one letter at a time.
    private val listOfInputLengths = mutableListOf<Int>()

    private val listOfLastKeys = mutableListOf<String>()

    init {
        displayedFormula = ""
        displayedNumber = ""
        canUseDecimal = true
        mSavedValue1 = fileManager.chooseFileType(TEMP_FILE, "one")
        mSavedValue2 = fileManager.chooseFileType(TEMP_FILE, "two")
        mSavedValue3 = fileManager.chooseFileType(TEMP_FILE, "three")
        mEquationHistory = fileManager.chooseFileType(FILE, "History.txt")
        mResultHistory = fileManager.chooseFileType(FILE, "Results.txt")
    }

    fun setValue(value: String) {
        //Big Text ANSWER
        mCallback!!.setValue(value, context)
        displayedNumber = value
    }

    private fun setFormula(value: String) { //Small text OPERATIONS
        mCallback!!.setFormula(value, context)
        displayedFormula = value
    }

    private fun addDigit(number: Int) {
        setFormula(number.toString())
    }

    private fun updateResult(value: Double) {
        setValue(Formatter.doubleToString(value))
    }

    private fun calculateResult(str: String) {
        val evaluator = ExtendedDoubleEvaluator()
        try {
            val result = evaluator.evaluate(str)
            updateResult(result)
        } catch (e: IllegalArgumentException) {
            setValue("NaN")
        }
    }

    fun handleOperationOnFormula(operation : String) {
        //if the last character of our formula is a digit and an operation is called from the list,
        //then add a multiplication before the operation
        if(displayedFormula.isNotEmpty()){
            if(listOfSpecialOperations.contains(operation) && listOfSpecialLastEntries.contains(getLastKey())) {
                setFormula("*")
                listOfInputLengths.add(1)
                listOfLastKeys.add("*")
            }
        }
        listOfInputLengths.add(getSign(operation).length)
        setFormula(getSign(operation))
        canUseDecimal = true
        listOfLastKeys.add(operation)
        liveUpdate()
    }

    fun handleStore(value : String, id: String) {
        if (displayedNumber != "") {
            when (id) {
                //SetFormula: small text, SetValue BIG TEXT
                MEMORY_ONE -> { mSavedValue1.writeText(value); setFormula(""); setValue(value) }
                MEMORY_TWO -> { mSavedValue2.writeText(value); setFormula(""); setValue(value)}
                MEMORY_THREE -> { mSavedValue3.writeText(value); setFormula(""); setValue(value) }
            }
        }
        else {
            val message = Toast.makeText(context, ERROR_SAVE_VALUE, Toast.LENGTH_SHORT)
            message.show()
        }
    }

    fun handleViewValue(id: String) {
        val message: Toast?
        val variable: String?

        when (id) {
            MEMORY_ONE -> { variable = mSavedValue1.readText()
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_TWO -> { variable = mSavedValue2.readText()
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
            MEMORY_THREE -> { variable = mSavedValue3.readText()
                if(variable == "") {
                    message = Toast.makeText(context, ERROR_READ_VALUE, Toast.LENGTH_SHORT)
                    message.show()
                }
                else {
                    setFormula(variable)
                }
            }
        }
    }

    fun handleClear(formula : String) {
        val newValue: String
        if(formula.isNotEmpty())
        {
            val removeThisManyCharacters = listOfInputLengths[listOfInputLengths.size - 1]
            newValue = formula.substring(0, (formula.length - removeThisManyCharacters))
            listOfInputLengths.removeAt(listOfInputLengths.size - 1)
            setFormula("")
            setFormula(newValue)
            setValue("")
            listOfLastKeys.remove(getLastKey())
        }
        if(mCallback!!.getFormula().isEmpty()){
            setValue("")
        }
        else{
            liveUpdate()
        }
    }

    fun handleReset() {
        listOfInputLengths.clear()
        listOfLastKeys.clear()
        canUseDecimal = true
        setValue("")
        setFormula("")
    }

    private fun handleEquals(str: String) {
        calculateResult(str)
    }

    fun storeHistory(equation: String) {
        val write: Writer = BufferedWriter(FileWriter(mEquationHistory, true))
        write.write(equation)
        write.appendln()
        write.flush()
        write.close()
    }

    fun storeResult(result: String) {
        val writer: Writer = BufferedWriter(FileWriter(mResultHistory, true))
        writer.write(result)
        writer.appendln()
        writer.flush()
        writer.close()
    }

//    fun deleteResult(value: String) {
//        //Readers
//        val readerEq: Reader = BufferedReader(FileReader(mEquationHistory.absolutePath))
//        val readerRes: Reader = BufferedReader(FileReader(mResultHistory.absolutePath))
//        //ArrayLists of values
//        val arrayListRes = ArrayList<String>(readerRes.readLines())
//        val arrayListEq = ArrayList<String>(readerEq.readLines())
//
//
//        //Get index of value to determine the equation to be removed
//        val index = arrayListRes.indexOf(value)
//        val equa = arrayListEq[index]
//
//        //Double check equation returns the result
//        val evaluator = ExtendedDoubleEvaluator()
//        val result = evaluator.evaluate(equa)
//
//        if(result.toDouble()==value.toDouble() || result.toString().contains(value) || result.compareTo(value.toDouble()) <= 0 )
//        {
//            arrayListRes.removeAt(index)
//            arrayListEq.removeAt(index)
//        }
//        else {
//            throw Exception("The Equation Array is not aligned to the results array.")
//        }
//        //Writers
//        val writerRes: Writer = BufferedWriter(FileWriter(mResultHistory, false))
//        val writerEq: Writer = BufferedWriter(FileWriter(mEquationHistory, false))
//
//        //Clears the file
//        writerEq.write("")
//        writerRes.write("")
//        writerRes.append()
//        writerRes.flush()
//        writerRes.close()
//        writerEq.append()
//        writerEq.flush()
//        writerEq.close()
//
//        arrayListEq.forEach {
//            storeHistory(it)
//        }
//
//        arrayListRes.forEach {
//            storeResult(it)
//        }
//    }

    fun getHistoryEntries(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        if (getResultFile().canRead()) {
            getText(list, getHistoryFile())
        }
        else {
            list.add("")
        }
        return list
    }

    fun getResults(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        if (getResultFile().canRead()) {
            getText(list, getResultFile())
        }
        else {
            list.add("")
        }
        return list
    }

    private fun getText(list: ArrayList<String>, file: File) {
        val reader: Reader = BufferedReader(FileReader(file.absolutePath))
        val temp = reader.readLines()
        if (temp.size <= 10) {
            var x = temp.size - 1
            while (x >= 0) {
                list.add(temp[x])
                x--
            }
        } else {
            var x = temp.size - 1
            var stop = 10
            while (stop >= 0) {
                list.add(temp[x])
                stop--
                x--
            }
        }
        reader.close()
    }

    private fun decimalClicked() {
        if(canUseDecimal) {
            setFormula(".")
            canUseDecimal = false
        }
    }


    private fun getSign(lastOperation: String?) = when (lastOperation) {
        PLUS -> "+"
        MINUS -> "-"
        MULTIPLY -> "*"
        DIVIDE -> "/"
        MODULO -> "%"
        POWER -> "^"
        ROOT -> "sqrt("
        LEFT_BRACKET -> "("
        RIGHT_BRACKET -> ")"
        PI -> "pi"
        E -> "e"
        SINE -> "sin("
        COSINE -> "cos("
        TANGENT -> "tan("
        LOGARITHM -> "log("
        NATURAL_LOGARITHM -> "ln("
        ARCSINE -> "asin("
        ARCCOS -> "acos("
        ARCTANGENT -> "atan("
        ROUNDING -> "round("
        CEILING -> "ceil("
        SQUARE -> "^(2)"
        CUBE -> "^(3)"
        ABSOLUTE_VALUE -> "abs("
        FLOOR -> "floor("
        else -> ""
    }

    fun numpadClicked(id: Int) {
        listOfInputLengths.add(1)
        if(listOfSpecialLastEntries.contains(getLastKey()) && getLastKey() != DIGIT){
            setFormula("*")
            listOfInputLengths.add(1)
        }
        listOfLastKeys.add(DIGIT)
        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> addDigit(0)
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
        }
        liveUpdate()
    }

    private fun getHistoryFile() : File {
        return mEquationHistory
    }

    private fun getResultFile() : File {
        return mResultHistory
    }

    private fun reciprocalOfResult(){
        val resultWithoutCommas = displayedNumber.replace(",", "")
        calculateResult("1/$resultWithoutCommas")
    }

    private fun randomNumberBetweenZeroAndResult(){
        val random = Random().nextDouble()
        setValue((random * displayedNumber.toDouble()).toString())
    }

    private fun negationOfResult(){
        val resultWithoutCommas = displayedNumber.replace(",", "")
        calculateResult("-$resultWithoutCommas")
    }

    fun handleOperationsOnResult(operation: String){
        if(displayedNumber.isNotEmpty() )
            if(!displayedNumber.toDouble().isNaN()){
                when(operation){
                    RECIPROCAL -> reciprocalOfResult()
                    RANDOM -> randomNumberBetweenZeroAndResult()
                    NEGATION -> negationOfResult()
                }
        }
        else
            Toast.makeText(context, ERROR_EMPTY_RESULT, Toast.LENGTH_SHORT).show()
    }

    private fun getLastKey() : String {
        if(listOfLastKeys.isEmpty())
            return ""
        return listOfLastKeys[listOfLastKeys.size - 1]
    }

    private fun liveUpdate() {
        try{
            handleEquals(mCallback!!.getFormula())
        }
        catch (e: IllegalAccessException){
            setValue("NaN")
        }
    }

    fun saveToHistory() : Boolean {
        return if (displayedNumber.replace(",","").toDoubleOrNull() == null || displayedNumber == "NaN"){
            Toast.makeText(context, "Invalid Save", Toast.LENGTH_SHORT).show()
            false
        }
        else{
            Toast.makeText(context, "Current State saved to History", Toast.LENGTH_SHORT).show()
            true
        }
    }
}