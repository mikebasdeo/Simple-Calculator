package com.simplemobiletools.calculator.activities
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.BinaryCalculator
import kotlinx.android.synthetic.main.activity_binary_calculator.*


class BinaryCalculatorActivity : SimpleActivity() {

    private lateinit var binaryCalculator: BinaryCalculator
    private lateinit var lastTouched: TextView

    @SuppressLint("MissingSuperCall", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_calculator)
        binaryCalculator = BinaryCalculator()
        lastTouched = binary_number_1

        binary_number_2.paintFlags = binary_number_2.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btn_0.setOnClickListener {
            lastTouched.text = lastTouched.text.toString() + 0
        }
        btn_1.setOnClickListener {
            lastTouched.text = lastTouched.text.toString() + 1
        }

        btn_convert.setOnClickListener{
            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                binary_number_1.requestFocus()
            }
                else if(!binary_number_2.text.isNullOrBlank()){
                "Please leave the ".toast(this)
            }
                else {
                    //TODO: Add operation symbol?
                    binary_result.text = binaryCalculator.convertBinary(binary_number_1.text.toString())
                }
        }

        binary_number_1.setOnTouchListener { input, event ->
            input.onTouchEvent(event)
            lastTouched = binary_number_1
            val noKeyboard = input.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            noKeyboard.hideSoftInputFromWindow(input.windowToken, 0)
        }

        binary_number_2.setOnTouchListener { input, event ->
            input.onTouchEvent(event)
            lastTouched = binary_number_2
            val noKeyboard = input.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            noKeyboard.hideSoftInputFromWindow(input.windowToken, 0)
        }

        btn_del.setOnClickListener {
            val text = lastTouched.text.toString()
            lastTouched.text = text.substring(0, text.length - 1)
        }

        btn_all_clear.setOnClickListener {
            binary_result.text = ""
            binary_number_2.text = ""
            binary_number_1.text = ""
            lastTouched = binary_number_1
            binary_number_1.requestFocus()
        }

        btn_plus.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.addBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }

        btn_minus.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.subtractBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }

        btn_multiply.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.multiplyBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }

        btn_divide.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.divideBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }
    }

    private fun Any.toast(context: Context) {
        Toast.makeText(context, this.toString(), Toast.LENGTH_LONG).show()
    }

    private fun missingNumber(): Boolean {
        return when {
            binary_number_1.text.isNullOrBlank() -> {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
                true
            }
            binary_number_2.text.isNullOrBlank() -> {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_2
                binary_number_2.requestFocus()
                true
            }
            else -> false
        }
    }
}
