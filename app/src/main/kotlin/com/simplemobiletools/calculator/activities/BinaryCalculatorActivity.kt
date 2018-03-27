package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.commons.extensions.performHapticFeedback
import kotlinx.android.synthetic.main.activity_binary_calculator.*
import android.text.InputType
import android.view.View.OnTouchListener
import com.simplemobiletools.calculator.helpers.BinaryCalculator


class BinaryCalculatorActivity : SimpleActivity(), Calculator {


    private lateinit var calc: CalculatorImpl

    private lateinit var binaryCalculator: BinaryCalculator
    private var vibrateOnButtonPress = true
    private lateinit var lastTouched: TextView
    private fun getButtonIds() = arrayOf(btn_0, btn_1)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_calculator)
        calc = CalculatorImpl(this, applicationContext)
        binaryCalculator = BinaryCalculator()


        binary_number_1.setPaintFlags(binary_number_1.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        binary_number_2.setPaintFlags(binary_number_2.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        lastTouched = binary_number_1

        //hookup for keypad
        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        binary_number_1.setOnTouchListener(OnTouchListener { v, event ->
            lastTouched = binary_number_1
            val inType = binary_number_1.getInputType()
            binary_number_1.setInputType(InputType.TYPE_NULL)
            binary_number_1.onTouchEvent(event)
            binary_number_1.setInputType(inType)
            true // consume touch event
        })

        binary_number_2.setOnTouchListener(OnTouchListener { v, event ->
            lastTouched = binary_number_2
            val inType = binary_number_2.getInputType()
            binary_number_2.setInputType(InputType.TYPE_NULL)
            binary_number_2.onTouchEvent(event)
            binary_number_2.setInputType(inType)
            true
        })

        btn_del.setOnClickListener {
            lastTouched.text = lastTouched.text.dropLast(1)
        }
        btn_all_clear.setOnClickListener {
            calc.handleReset()
            binary_number_1.setText("")
            binary_number_2.setText("")
            binary_result.text = ""
            binary_number_1.requestFocus()
        }

        btn_plus.setOnClickListener{
            //TODO:Add null check for EditText fields. Throws error at the moment.
            binary_result.text = binaryCalculator.addBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }
    }

    override fun setValue(value: String, context: Context) {
        lastTouched.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        calc.setValue(Formatter.doubleToString(d))
    }

    override fun setFormula(value: String, context: Context) {
        if(value == ""){
            lastTouched.text = ""
        }
        else{
           lastTouched.text = lastTouched.text.toString() + value
        }
    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }
}
