package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.simplemobiletools.calculator.BuildConfig
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.export.ExportManager
import com.simplemobiletools.calculator.helpers.CONSTANT.FILE
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.FileHandler
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.helpers.LICENSE_ESPRESSO
import com.simplemobiletools.commons.helpers.LICENSE_KOTLIN
import com.simplemobiletools.commons.helpers.LICENSE_ROBOLECTRIC
import kotlinx.android.synthetic.main.activity_history.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*

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
        when (item.itemId) {
            R.string.export -> exportData()
            R.string.settings -> launchSettings()
            R.string.about -> launchAbout()
            R.string.unit_conversion -> launchUnitConversion()
            R.string.binary_calculator -> launchBinaryCalculator()
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
        exportFile = fileManager.chooseFileType(FILE, "Export")

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
            //Text View
            textViewRes.text = it
            textViewRes.gravity = R.id.center or R.id.top
            textViewRes.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textViewRes.setTextColor(getColor(R.color.white))
            //textViewRes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
            //Table row

            tbrow.addView(textViewRes)
            resultsText.addView(tbrow)
            //temp1 = temp1 +  /*value1.toString()". " +*/ it + "\n"
            //value1++
        }

        equations.forEach {
            val textViewEq = TextView(this)
            textViewEq.text = it
            textViewEq.gravity = R.id.center or R.id.top
            textViewEq.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textViewEq.setTextColor(getColor(R.color.white))
            //textViewEq.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
            table_equations.addView(textViewEq)
            //temp2 = temp2 + /*value2.toString() ". " +*/ it + "\n"
            //value2++
        }

    }

    private fun exportData() {
        export = ExportManager()
        val writer: Writer = BufferedWriter(FileWriter(exportFile, true))
        export.writeLine(writer, equations)
        export.writeLine(writer, results)
        writer.flush()
        writer.close()
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
}