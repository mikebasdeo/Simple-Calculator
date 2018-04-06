package com.simplemobiletools.calculator.activities
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.BinaryCalculator
import kotlinx.android.synthetic.main.activity_binary_calculator.*
import kotlinx.android.synthetic.main.activity_binary_calculator.view.*


class BinaryCalculatorActivity : SimpleActivity() {

    private lateinit var binaryCalculator: BinaryCalculator
    private lateinit var lastTouched: TextView
    private  var isTextOne : Boolean = true

    @SuppressLint("MissingSuperCall", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_calculator)
        binaryCalculator = BinaryCalculator()
        lastTouched = binary_number_1


        binary_number_2.paintFlags = binary_number_2.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        btn_0.setOnClickListener {
            lastTouched.text = lastTouched.text.toString() + 0

            if(lastTouched == binary_number_1){
                label_number_1.text = binaryCalculator.convertBinary(lastTouched.text.toString())
            }

            if(lastTouched == binary_number_2){
                label_number_2.text = binaryCalculator.convertBinary(lastTouched.text.toString())
            }
            binary_result.text = ""


        }
        btn_1.setOnClickListener {
            lastTouched.text = lastTouched.text.toString() + 1

            if(lastTouched == binary_number_1){
                label_number_1.text = binaryCalculator.convertBinary(lastTouched.text.toString())
            }

            if(lastTouched == binary_number_2){
                label_number_2.text = binaryCalculator.convertBinary(lastTouched.text.toString())
            }
            binary_result.text = ""
        }


        binary_number_1.setOnTouchListener { input, event ->
            input.onTouchEvent(event)
            isTextOne = true
            lastTouched = binary_number_1
            binary_result.text = ""
            val noKeyboard = input.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            noKeyboard.hideSoftInputFromWindow(input.windowToken, 0)
        }

        binary_number_2.setOnTouchListener { input, event ->
            input.onTouchEvent(event)

            lastTouched = binary_number_2
            binary_result.text = ""
            val noKeyboard = input.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            noKeyboard.hideSoftInputFromWindow(input.windowToken, 0)
        }

        btn_del.setOnClickListener {
            val text = lastTouched.text.toString()
            if(!lastTouched.text.isNullOrEmpty())
            {
                lastTouched.text = text.substring(0, text.length - 1)

                resetOperatorColours()
                binary_result.text =""
            }
        }

        btn_all_clear.setOnClickListener {
            binary_result.text = ""
            binary_number_2.text = ""
            binary_number_1.text = ""
            label_number_1.text = ""
            label_number_2.text = ""
            label_result.text = ""
            lastTouched = binary_number_1
            binary_number_1.requestFocus()
            resetOperatorColours()

        }

        btn_plus.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.addBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
                changeOperatorButtonColors(btn_plus)
        }

        btn_and.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.andBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }

        btn_or.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.orBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }

        btn_xor.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.xorBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
        }


        fun TextView.onChange(cb: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) { cb(s.toString()) }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        binary_result.onChange {
                label_result.text = binary_result.text
                if(label_result.text.length > 1){
                    label_result.text = binaryCalculator.convertBinary(label_result.text.toString())
                }
        }
        binary_number_1.onChange {
            label_number_1.text = binary_number_1.text
            if(label_number_1.text.length > 1){
                label_number_1.text = binaryCalculator.convertBinary(label_number_1.text.toString())
            }
        }

        binary_number_2.onChange {
            label_number_2.text = binary_number_2.text
            if(label_number_2.text.length > 1){
                label_number_2.text = binaryCalculator.convertBinary(label_number_2.text.toString())
            }
        }

        btn_minus.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.subtractBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
                changeOperatorButtonColors(btn_minus)
        }

        btn_multiply.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.multiplyBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
                changeOperatorButtonColors(btn_multiply)
        }

        btn_divide.setOnClickListener{
            if(!missingNumber())
                binary_result.text = binaryCalculator.divideBinary(binary_number_1.text.toString(), binary_number_2.text.toString())
                changeOperatorButtonColors(btn_divide)
        }
    }

    private fun resetOperatorColours(){
        btn_plus.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_plus.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_minus.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_minus.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_multiply.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_multiply.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_divide.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_divide.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
    }

    private fun changeOperatorButtonColors(button: Button){
        btn_plus.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_plus.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_minus.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_minus.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_multiply.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_multiply.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))
        btn_divide.setTextColor(ContextCompat.getColor(this, R.color.white))
        btn_divide.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_3))

        button.setTextColor(ContextCompat.getColor(this, R.color.noah_4))
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.noah_5))

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
