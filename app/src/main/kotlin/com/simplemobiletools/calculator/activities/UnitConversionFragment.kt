package com.simplemobiletools.calculator.activities


import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.conversions.*
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.commons.extensions.performHapticFeedback
import kotlinx.android.synthetic.main.fragment_unit_conversion.*
import java.math.BigDecimal
import java.text.DecimalFormat


class UnitConversionFragment : Fragment(), Calculator {

    lateinit var calc: CalculatorImpl
    private lateinit var converter: Converter

    private var vibrateOnButtonPress = true
    private fun getDigitIds() = listOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unit_conversion, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        calc = CalculatorImpl(this, activity!!.applicationContext)

        getDigitIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); liveUpdate(); checkHaptic(it) }
        }
        btn_decimal.setOnClickListener { decimalClicked(); checkHaptic(it) }
        btn_del.setOnClickListener { before.text = before.text.dropLast(1); liveUpdate(); checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset(); after.text = ""; checkHaptic(it) }
        btn_swap.setOnClickListener { swap(); checkHaptic(it) }
        btn_save.setOnClickListener { calc.storeHistory(getFormula()); calc.storeResult(after.text.toString())}

        before.setOnLongClickListener { pasteFromClipBoard() }

        //Three drop down menus. The conversionChoiceSpinner changes the other two automatically.
        val conversionChoiceSpinner: Spinner = conversion_type_spinner
        val unitsBeforeSpinner: Spinner = units_before_spinner
        val unitsAfterSpinner: Spinner = units_after_spinner



        //Main List for conversion choices from helper.
        val conversionChoiceList = listOf("Distance", "Speed", "Time", "Volume", "Weight", "Temperature")

        //Empty list that will be populated with the relevant conversion units.
        val unitList = ArrayList<String>()

        val choiceAdapter: ArrayAdapter<String>
        val beforeAdapter: ArrayAdapter<String>
        val afterAdapter: ArrayAdapter<String>

        //Create adapters for each of the three spinners.
        choiceAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, conversionChoiceList)
        beforeAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item_units, unitList)
        afterAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item_units, unitList)

        //Connect each spinner to its respective adapter.
        conversionChoiceSpinner.adapter = choiceAdapter
        unitsBeforeSpinner.adapter = beforeAdapter
        unitsAfterSpinner.adapter = afterAdapter

        conversionChoiceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        unitsBeforeSpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                liveUpdate()
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
                //Auto-generated method stub
            }
        }

        unitsAfterSpinner.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                liveUpdate()
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

        before.text = before.text.toString().replace(",", "")

        if(before.text.isNullOrBlank()) {
            before.text = ""
        }

        var input = before.text.toString().toDoubleOrNull()

        if(input == null)
            input = 0.0

        val result = converter.calculate(
                input,
                units_before_spinner.selectedItem.toString(),
                units_after_spinner.selectedItem.toString()
        )
        before_abbr.text = converter.getMap().getValue(units_before_spinner.selectedItem.toString()).second
        after_abbr.text = converter.getMap().getValue(units_after_spinner.selectedItem.toString()).second
        after.text = trimResult(result)
        if(input == 0.0)
            after.text = ""
    }

    private fun decimalClicked() {
        if(before.text.isNullOrEmpty())
            before.text = "0."
        else
            if(!before.text.contains("."))
                setFormula(".", activity!!.applicationContext)
    }

    private fun trimResult(input: Double): String{
        val outForm = DecimalFormat("#,###.0000")
        var rawOut = outForm.format(BigDecimal(input).setScale(4, BigDecimal.ROUND_HALF_UP).toDouble())

        if (rawOut[0] == '.')
            rawOut = "0$rawOut"

        for (i in rawOut.length-1 downTo 0) {
            if (rawOut.endsWith('.')) {
                rawOut = rawOut.dropLast(1)
                break
            }
            else if (rawOut.endsWith('0')) {
                rawOut = rawOut.dropLast(1)
            }
        }

        if (rawOut.endsWith('.')) rawOut = rawOut.dropLast(1)

        return rawOut
    }

    private fun pasteFromClipBoard(): Boolean {
        //check clipboard
        val clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (!clipboard.primaryClip.getItemAt(0).coerceToText(activity!!.applicationContext).toString().isEmpty()) {
            before.text = clipboard.primaryClip.getItemAt(0).text.toString()
            Toast.makeText(activity!!.applicationContext,"Pasted from clipboard", Toast.LENGTH_LONG).show()
            return true
        }
        else {
            // do nothing

            return false
        }
    }

    //private fun String.isNum() = matches(Regex("\\d|\\d{2}|\\d{3}(\\d{3},)+(.|)(\\d)+"))

//TODO: This stopped working when moving to fragments for some reason?
//    private fun copyToClipboard(copyResult: Boolean): Boolean {
//        var value = after.value
//        if (copyResult) {
//            value = after.value
//        }
//
//        return if (value.isEmpty()) {
//            false
//        } else {
//            copyToClipboard(value)
//            true
//        }
//    }

    private fun swap() {
        val oldBefore = units_before_spinner.selectedItemPosition
        val oldAfter = units_after_spinner.selectedItemPosition

        units_before_spinner.setSelection(oldAfter)
        units_after_spinner.setSelection(oldBefore)
    }


}