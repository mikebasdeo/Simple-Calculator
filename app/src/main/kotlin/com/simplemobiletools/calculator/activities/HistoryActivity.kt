package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.annotation.Size
import android.util.TypedValue
import android.widget.*
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import kotlinx.android.synthetic.main.activity_history.*

/**
 * Created by Marc-Andre Dragon on 2018-03-01.
 */
class HistoryActivity : SimpleActivity(), Calculator {

    private lateinit var calc: CalculatorImpl
    private lateinit var equations: ArrayList<String>
    private lateinit var results: ArrayList<String>

    @SuppressLint("MissingSuperCall", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        calc = CalculatorImpl(this, applicationContext)
        results = calc.getResults()
        equations = calc.getHistoryEntries()

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
            delbtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.toFloat())
            delbtn.setOnClickListener {
                calc.deleteResult(textViewRes.text.toString())
            }
            //Text View
            textViewRes.text = it
            textViewRes.gravity = R.id.center or R.id.top
            textViewRes.setTextColor(getColor(R.color.white))
            textViewRes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
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
            textViewEq.gravity = R.id.center or R.id.top
            textViewEq.setTextColor(getColor(R.color.white))
            textViewEq.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat())
            table_equations.addView(textViewEq)
            //temp2 = temp2 + /*value2.toString() ". " +*/ it + "\n"
            //value2++
        }
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