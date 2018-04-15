package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.export.ExportManager
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.FileHandler
import kotlinx.android.synthetic.main.activity_history.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import com.simplemobiletools.calculator.helpers.CONSTANT.HISTORY_FILE
import com.simplemobiletools.commons.extensions.getSDCardPath
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.helpers.LICENSE_ESPRESSO
import com.simplemobiletools.commons.helpers.LICENSE_KOTLIN
import com.simplemobiletools.commons.helpers.LICENSE_ROBOLECTRIC
import java.util.*
import com.opencsv.CSVWriter

/**
 * Created by Marc-Andre Dragon on 2018-03-01.
 */
class HistoryActivity : SimpleActivity(), Calculator {

    private lateinit var calc: CalculatorImpl
    private lateinit var equations: ArrayList<String>
    private lateinit var results: ArrayList<String>
    private lateinit var export: ExportManager
    private lateinit var  exportFile: File
    private var index = 0


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (index == 0) {
            menu?.add(R.string.about)
            menu?.add(R.string.binary_calculator)
            menu?.add(R.string.export)
            menu?.add(R.string.settings)
            menu?.add(R.string.unit_conversion)
            index++
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title.toString()) {
            getString(R.string.export) -> exportData()
            getString(R.string.settings) -> launchSettings()
            getString(R.string.about) -> launchAbout()
            getString(R.string.unit_conversion) -> launchUnitConversion()
            getString(R.string.binary_calculator) -> launchBinaryCalculator()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("MissingSuperCall", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        calc = CalculatorImpl(this, applicationContext)
        results = calc.getResults()
        equations = calc.getHistoryEntries()
        val fileManager = FileHandler(this)
        if (isExternalStorageWritable()) {
            exportFile = fileManager.chooseFileType(HISTORY_FILE, applicationContext.getSDCardPath()+"/Export")
        }
        val equationsText = findViewById<TableLayout>(R.id.table_equations)
        val resultsText = findViewById<TableLayout>(R.id.table_results)

        val scrollEquations = ScrollView(this)
        val scrollResults = ScrollView(this)

        resultsText.addView(scrollResults)
        equationsText.addView(scrollEquations)

        //var value1 = 1; var value2 = 1
        results.forEach {
            val tbrow = TableRow(this)
            val textViewRes = TextView(this)
            val delbtn = Button(this)

            //Delete button
            delbtn.text = getText(R.string.delete)
            delbtn.setTextColor(getColor(R.color.white))
            //delbtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.toFloat())
            delbtn.setOnClickListener {
                calc.deleteResult(textViewRes.text.toString())
                finish()
                startActivity(intent)
            }
            //Text View
            textViewRes.text = it
            textViewRes.gravity = R.id.center
            textViewRes.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textViewRes.autoSizeMaxTextSize
            textViewRes.setTextColor(getColor(R.color.white))
            //textViewRes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
            //Table row

            tbrow.addView(delbtn)
            tbrow.addView(textViewRes)
            resultsText.addView(tbrow)
            //temp1 = temp1 +  /*value1.toString()". " +*/ it + "\n"
            //value1++
        }

        equations.forEach {
            val textViewEq = TextView(this)
            textViewEq.text = it
            textViewEq.gravity = R.id.center
            textViewEq.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textViewEq.setTextColor(getColor(R.color.white))
            textViewEq.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
            table_equations.addView(textViewEq)
            //temp2 = temp2 + /*value2.toString() ". " +*/ it + "\n"
            //value2++
        }

    }

    private fun exportData() {
        //Gets the SD cards absolute Path
        val dir = android.os.Environment.getExternalStorageDirectory().absolutePath
        val fileName = "Export.csv"
        //Creates the path name
        val path = dir + File.separator + fileName
        //Creates the file with the fileManager Object
        val fileManager = FileHandler(this)
        val f = fileManager.chooseFileType(HISTORY_FILE, path)
        //Create CSV Writer
        val writerCSV: CSVWriter
        val fileWriter: FileWriter
        // File exist and it is not a directory
        if (f.exists() && !f.isDirectory) {
            fileWriter = FileWriter(path, false)
            writerCSV = CSVWriter(fileWriter)
        } else {
            writerCSV = CSVWriter(FileWriter(path))
        }
        export = ExportManager()
        export.writeLine(writerCSV, equations)
        export.writeLine(writerCSV, results)
        writerCSV.flush()
        writerCSV.close()
    }

    private fun launchUnitConversion(){
        startActivity(Intent(applicationContext, UnitConversionActivity::class.java))
    }
    private fun launchBinaryCalculator(){
        startActivity(Intent(applicationContext, BinaryCalculatorActivity::class.java))
    }

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        startAboutActivity(R.string.app_name, LICENSE_KOTLIN or LICENSE_AUTOFITTEXTVIEW or LICENSE_ROBOLECTRIC or LICENSE_ESPRESSO, BuildConfig.VERSION_NAME)
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
    }

    override fun setValue(value: String, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValueDouble(d: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFormula(value: String, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFormula(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}