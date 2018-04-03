package com.simplemobiletools.calculator.activities
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import com.simplemobiletools.calculator.R
import kotlinx.android.synthetic.main.activity_binary_calculator.*
import android.widget.Toast
import com.simplemobiletools.calculator.helpers.BinaryCalculator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class BinaryCalculatorActivity : SimpleActivity() {

    private lateinit var binaryCalculator: BinaryCalculator
    private var vibrateOnButtonPress = true
    private lateinit var lastTouched: EditText
    private fun getButtonIds() = arrayOf(btn_0, btn_1)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_calculator)
        binaryCalculator = BinaryCalculator()
        lastTouched = binary_number_1

        binary_number_2.paintFlags = binary_number_2.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btn_0.setOnClickListener {
            var curPosition = lastTouched.selectionStart
            lastTouched.text.insert(curPosition, "0")
        }
        btn_1.setOnClickListener {
            var curPosition = lastTouched.selectionStart
            lastTouched.text.insert(curPosition, "1")
        }

        btn_convert.setOnClickListener{
            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
            }
                else if(!binary_number_2.text.isNullOrBlank()){
                "Please leave this section empty for conversion".toast(this)
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
            val text = lastTouched.getText().toString()
            lastTouched.setText(text.substring(0, text.length - 1))
        }

        btn_all_clear.setOnClickListener {
            binary_result.text = ""
            binary_number_2.setText("")
            binary_number_1.setText("")
            lastTouched = binary_number_1
            binary_number_1.requestFocus()
        }

        btn_plus.setOnClickListener{

            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
            }
            else if (binary_number_2.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_2
                binary_number_2.requestFocus()
            }
            else {
                //TODO: Add operation symbol?
                binary_result.text = binaryCalculator.addBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
            }
        }

        btn_minus.setOnClickListener{

            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
            }
            else if (binary_number_2.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_2
                binary_number_2.requestFocus()
            }
            else {
                binary_result.text = binaryCalculator.subtractBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
            }
        }

        btn_multiply.setOnClickListener{

            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
            }
            else if (binary_number_2.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_2
                binary_number_2.requestFocus()
            }
            else {
                binary_result.text = binaryCalculator.multiplyBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
            }
        }

        btn_divide.setOnClickListener{

            if(binary_number_1.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_1
                binary_number_1.requestFocus()
            }
            else if (binary_number_2.text.isNullOrBlank()) {
                "Please enter a valid binary number".toast(this)
                lastTouched = binary_number_2
                binary_number_2.requestFocus()
            }
            else {
                binary_result.text = binaryCalculator.divideBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
            }
        }
    }

    fun Any.toast(context: Context) {
        Toast.makeText(context, this.toString(), Toast.LENGTH_LONG).show()
    }
}
