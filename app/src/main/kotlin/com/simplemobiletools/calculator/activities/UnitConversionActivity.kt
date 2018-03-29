package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.conversions.*
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.commons.extensions.performHapticFeedback
import kotlinx.android.synthetic.main.activity_unit_conversion.*


class UnitConversionActivity : SimpleActivity(), Calculator {

    private lateinit var calc: CalculatorImpl
    private lateinit var converter: Converter

    private var vibrateOnButtonPress = true
    private fun getDigitIds() = listOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)
        calc = CalculatorImpl(this, applicationContext)

        getDigitIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); liveUpdate(); checkHaptic(it) }
        }
        btn_decimal.setOnClickListener { decimalClicked(); checkHaptic(it);}
        btn_del.setOnClickListener { before.text = before.text.dropLast(1); liveUpdate(); checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset(); after.text = ""; checkHaptic(it);}

        //Three drop down menus. The conversionChoiceSpinner changes the other two automatically.
        val conversionChoiceSpinner: Spinner = findViewById(R.id.conversion_type_spinner)
        val unitsBeforeSpinner: Spinner = findViewById(R.id.units_before_spinner)
        val unitsAfterSpinner: Spinner = findViewById(R.id.units_after_spinner)

        //Main List for conversion choices from helper.
        val conversionChoiceList = listOf("Distance", "Speed", "Time", "Volume", "Weight", "Temperature")

        //Empty list that will be populated with the relevant conversion units.
        val unitList = ArrayList<String>()

        val choiceAdapter: ArrayAdapter<String>
        val beforeAdapter: ArrayAdapter<String>
        val afterAdapter: ArrayAdapter<String>

        //Create adapters for each of the three spinners.
        choiceAdapter = ArrayAdapter(this, R.layout.spinner_item, conversionChoiceList)
        beforeAdapter = ArrayAdapter(this, R.layout.spinner_item_units, unitList)
        afterAdapter = ArrayAdapter(this, R.layout.spinner_item_units, unitList)

        //Connect each spinner to its respective adapter.
        conversionChoiceSpinner.adapter = choiceAdapter
        unitsBeforeSpinner.adapter = beforeAdapter
        unitsAfterSpinner.adapter = afterAdapter

        conversionChoiceSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {
                val s = conversionChoiceSpinner.getItemAtPosition(arg2).toString()
                when (s){
                    "Speed" -> converter = SpeedConversion()
                    "Distance" -> converter = LengthConversion()
                    "Weight" -> converter = WeightConversion()
                    "Time" -> converter = TimeConversion()
                    "Volume" -> converter = VolumeConversion()
                    "Temperature" -> converter = TemperatureConversion()
                }
                unitList.clear()
                for(m in converter.getMap())
                    unitList.add(m.key)
                unitsBeforeSpinner.setSelection(0)
                beforeAdapter.notifyDataSetChanged()
                afterAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
                //Auto-generated method stub
            }
        }
    }

    override fun setValue(value: String, context: Context) {
        before.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        calc.setValue(Formatter.doubleToString(d))
    }

    @SuppressLint("SetTextI18n")
    override fun setFormula(value: String, context: Context) {
        if(value == ""){
            before.text = ""
        }
        else{
            before.text = before.text.toString().replace(",","") + value
        }
    }
    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    override fun getFormula(): String {
        return before.text.toString()
    }

    private fun liveUpdate() {

        if(before.text.isNullOrBlank())
            before.text = ""

        var input = before.text.toString().replace(",","").toDoubleOrNull()

        if(input == null)
            input = 0.0

        val res = converter.calculate(
                    input,
                    units_before_spinner.selectedItem.toString(),
                    units_after_spinner.selectedItem.toString()
                    )

        after.text = res.toString()
        before_abbr.text = converter.getMap().getValue(units_before_spinner.selectedItem.toString()).second
        after_abbr.text = converter.getMap().getValue(units_after_spinner.selectedItem.toString()).second
    }

    private fun decimalClicked() {
        if(before.text.isNullOrEmpty())
            before.text = "0."
        else
            if(!before.text.contains("."))
                setFormula(".", this)
    }
}
