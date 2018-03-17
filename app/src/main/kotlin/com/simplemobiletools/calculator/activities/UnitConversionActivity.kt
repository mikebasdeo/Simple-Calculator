package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.*
import com.simplemobiletools.commons.extensions.performHapticFeedback
import kotlinx.android.synthetic.main.activity_main.*


class UnitConversionActivity : SimpleActivity(), Calculator {

    private lateinit var calc: CalculatorImpl
    private var vibrateOnButtonPress = true
    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)


    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)
        calc = CalculatorImpl(this, applicationContext)

        btn_del.setOnClickListener {calc.handleClear(formula.text.toString()); checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset()}

        //create lengthConversion helper object.
        var lengthConversion : lengthConversion = lengthConversion()

        //Three drop down menus. The conversionChoiceSpiner changes the other two automatically.
        val conversionChoiceSpinner: Spinner
        val unitsBeforeSpinner: Spinner
        val unitsAfterSpinner: Spinner

        //Main List for conversion choices from helper.
        val conversionChoiceList = lengthConversion.conversionChoiceList

        //Empty list that will be populated with the relevant conversion units.
        val unitList = ArrayList<String>()

        val choiceAdapter: ArrayAdapter<String>
        val beforeAdapter: ArrayAdapter<String>
        val afterAdapter: ArrayAdapter<String>

        //hookup for keypad
        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        //Create adapters for each of the three spinners.
        //TODO: Make custom layouts for the drop down menus.
        choiceAdapter = ArrayAdapter(this, R.layout.spinner_item, conversionChoiceList)
        beforeAdapter = ArrayAdapter(this, R.layout.spinner_item, unitList)
        afterAdapter = ArrayAdapter(this, R.layout.spinner_item, unitList)

        //Connect to layout.
        conversionChoiceSpinner = findViewById(R.id.conversion_type_spinner) as Spinner
        unitsBeforeSpinner = findViewById(R.id.units_before_spinner) as Spinner
        unitsAfterSpinner = findViewById(R.id.units_after_spinner) as Spinner


        //Connect each spinner to its respective adapter.
        conversionChoiceSpinner.setAdapter(choiceAdapter)
        unitsBeforeSpinner.setAdapter(beforeAdapter)
        unitsAfterSpinner.setAdapter(afterAdapter)

        conversionChoiceSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            //TODO: Put all this inside lengthConversion.java helper.
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {
                val s = conversionChoiceSpinner.getItemAtPosition(arg2).toString()
                if(s == "Distance"){
                    unitList.clear()
                    unitList.add("Km")
                    unitList.add("m")
                    unitList.add("cm")
                }
                if(s == "Speed"){
                    unitList.clear()
                    unitList.add("Velocity")
                    unitList.add("Acceleration")
                    unitList.add("Displacement")
                }
                if (s == "Weight"){
                    unitList.clear()
                    unitList.add("Kg")
                    unitList.add("Pounds")
                    unitList.add("Newtons")
                }
                beforeAdapter.notifyDataSetChanged()
                afterAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
    }

    override fun setValue(value: String, context: Context) {
        formula.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        calc.setValue(Formatter.doubleToString(d))
        calc.lastKey = CONSTANT.DIGIT
    }

    override fun setFormula(value: String, context: Context) {
        if(value == ""){
            formula.text = ""
        }
        else{
            formula.text = formula.text.toString() + value
        }
    }
    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

}
