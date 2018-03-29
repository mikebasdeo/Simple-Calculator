package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.conversions.*
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.commons.extensions.copyToClipboard
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.value
import kotlinx.android.synthetic.main.activity_unit_conversion.*


class UnitConversionActivity : SimpleActivity(), Calculator {

    private lateinit var calc: CalculatorImpl
    private lateinit var converter: Converter

    private var vibrateOnButtonPress = true
    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)



    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)
        calc = CalculatorImpl(this, applicationContext)

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }
        btn_del.setOnClickListener { before.text = before.text.dropLast(1); after.text = ""; checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset(); after.text = ""}
        btn_divide.setOnClickListener { swap() }
        btn_equals.setOnClickListener{
            var res =   converter.calculate(
                        before.text.toString().toDoubleOrNull(),
                        units_before_spinner.selectedItem.toString(),
                        units_after_spinner.selectedItem.toString()
                        )
            after.text=res.toString()
            before_abbr.text = converter.getMap().getValue(units_before_spinner.selectedItem.toString()).second
            after_abbr.text = converter.getMap().getValue(units_after_spinner.selectedItem.toString()).second
        }

        after.setOnLongClickListener { copyToClipboard(true) }
        before.setOnLongClickListener { pasteFromClipBoard() }

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
        //TODO: Make custom layouts for the drop down menus.
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
                // TODO Auto-generated method stub
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

    override fun setFormula(value: String, context: Context) {
        if(value == ""){
            before.text = ""
        }
        else{
            before.text = before.text.toString() + value
        }
    }
    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun pasteFromClipBoard(): Boolean {
        //check clipboard
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (!clipboard.primaryClip.getItemAt(0).coerceToText(this).toString().isEmpty()) {
            before.text = clipboard.primaryClip.getItemAt(0).getText().toString()
            Toast.makeText(applicationContext,"Pasted from clipboard", Toast.LENGTH_LONG).show()
            return true
        }
        else {
            // do nothing

            return false
        }
    }

    //private fun String.isNum() = matches(Regex("\\d|\\d{2}|\\d{3}(\\d{3},)+(.|)(\\d)+"))

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = after.value
        if (copyResult) {
            value = after.value
        }

        return if (value.isEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    private fun swap() {
        var oldBefore = units_before_spinner.getSelectedItemPosition()
        var oldAfter = units_after_spinner.getSelectedItemPosition()

        units_before_spinner.setSelection(oldAfter)
        units_after_spinner.setSelection(oldBefore)

    }
}
