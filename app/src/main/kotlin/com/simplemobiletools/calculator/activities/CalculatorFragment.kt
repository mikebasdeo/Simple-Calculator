package com.simplemobiletools.calculator.activities


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.CONSTANT
import com.simplemobiletools.calculator.helpers.CONSTANT.ABSOLUTE_VALUE
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCCOS
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCSINE
import com.simplemobiletools.calculator.helpers.CONSTANT.ARCTANGENT
import com.simplemobiletools.calculator.helpers.CONSTANT.CEILING
import com.simplemobiletools.calculator.helpers.CONSTANT.COSINE
import com.simplemobiletools.calculator.helpers.CONSTANT.CUBE
import com.simplemobiletools.calculator.helpers.CONSTANT.E
import com.simplemobiletools.calculator.helpers.CONSTANT.FLOOR
import com.simplemobiletools.calculator.helpers.CONSTANT.LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.MODULO
import com.simplemobiletools.calculator.helpers.CONSTANT.NATURAL_LOGARITHM
import com.simplemobiletools.calculator.helpers.CONSTANT.NEGATION
import com.simplemobiletools.calculator.helpers.CONSTANT.PI
import com.simplemobiletools.calculator.helpers.CONSTANT.POWER
import com.simplemobiletools.calculator.helpers.CONSTANT.RANDOM
import com.simplemobiletools.calculator.helpers.CONSTANT.RECIPROCAL
import com.simplemobiletools.calculator.helpers.CONSTANT.ROOT
import com.simplemobiletools.calculator.helpers.CONSTANT.ROUNDING
import com.simplemobiletools.calculator.helpers.CONSTANT.SINE
import com.simplemobiletools.calculator.helpers.CONSTANT.SQUARE
import com.simplemobiletools.calculator.helpers.CONSTANT.TANGENT
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CalculatorImpl
import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.value
import kotlinx.android.synthetic.main.fragment_calculator.*
import me.grantland.widget.AutofitHelper


/**
 * A simple [Fragment] subclass.
 */
class CalculatorFragment : Fragment(), Calculator {

    //ATTRIBUTES


    private var vibrateOnButtonPress = true
    private var shiftClicked = false
    private lateinit var  calc: CalculatorImpl


    //CONSTRUCTORS
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }


    //METHODS
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        calc = CalculatorImpl(this, activity!!.applicationContext)


        //Never changes
        btn_plus.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.PLUS); checkHaptic(it) }
        btn_minus.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.MINUS); checkHaptic(it) }
        btn_multiply.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.MULTIPLY); checkHaptic(it) }
        btn_divide.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.DIVIDE); checkHaptic(it) }
        btn_memory_1.setOnClickListener { calc.handleViewValue(CONSTANT.MEMORY_ONE)}
        btn_memory_1.setOnLongClickListener{ calc.handleStore(result.text.toString(), CONSTANT.MEMORY_ONE); true }
        btn_memory_2.setOnClickListener { calc.handleViewValue(CONSTANT.MEMORY_TWO)}
        btn_memory_2.setOnLongClickListener{ calc.handleStore(result.text.toString(), CONSTANT.MEMORY_TWO); true }
        btn_memory_3.setOnClickListener { calc.handleViewValue(CONSTANT.MEMORY_THREE) }
        btn_memory_3.setOnLongClickListener{calc.handleStore(result.text.toString(), CONSTANT.MEMORY_THREE); true }
        btn_del.setOnClickListener {calc.handleClear(formula.text.toString()); checkHaptic(it) }
        btn_all_clear.setOnClickListener { calc.handleReset()}
        btn_left_bracket.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.LEFT_BRACKET); checkHaptic(it) }
        btn_right_bracket.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.RIGHT_BRACKET); checkHaptic(it) }


        btn_shift.setOnClickListener {
            shiftClicked = !shiftClicked
            changeButtonFunctionality(shiftClicked)
        }

        getDigitIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_save.setOnClickListener {
            if(calc.saveToHistory()) {
                calc.storeHistory(getFormula())
                calc.storeResult(result.text.toString())
            }
        }

        formula.setOnLongClickListener { copyToClipboard(false) }
        formula.setOnLongClickListener{ pasteFromClipBoard()}
        result.setOnLongClickListener { copyToClipboard(true) }

        AutofitHelper.create(result)
        AutofitHelper.create(formula)

        changeButtonFunctionality(shiftClicked)

    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun changeButtonFunctionality(shiftClicked: Boolean){
        if(shiftClicked){
            btn_shift.setBackgroundResource(R.drawable.shift2btn)
            btn_pi_rand.setBackgroundResource(R.drawable.randbtn)
            btn_sin_asin.setBackgroundResource(R.drawable.cosin)
            btn_cos_acos.setBackgroundResource(R.drawable.cocos)
            btn_tan_atan.setBackgroundResource(R.drawable.cotan)
            btn_reciprocal_round.setBackgroundResource(R.drawable.round)
            btn_log_ceil.setBackgroundResource(R.drawable.ceil)
            btn_root_square.setBackgroundResource(R.drawable.xsquare)
            btn_mod_cube.setBackgroundResource(R.drawable.xcubed)
            btn_power_abs.setBackgroundResource(R.drawable.abs)
            btn_e_neg.setBackgroundResource(R.drawable.plusminus)
            btn_ln_floor.setBackgroundResource(R.drawable.floor)
            btn_mod_cube.setOnClickListener { calc.handleOperationOnFormula(CUBE); checkHaptic(it) }
            btn_power_abs.setOnClickListener { calc.handleOperationOnFormula(ABSOLUTE_VALUE); checkHaptic(it) }
            btn_root_square.setOnClickListener { calc.handleOperationOnFormula(SQUARE); checkHaptic(it) }
            btn_pi_rand.setOnClickListener { calc.handleOperationsOnResult(RANDOM); checkHaptic(it) }
            btn_sin_asin.setOnClickListener { calc.handleOperationOnFormula(ARCSINE); checkHaptic(it) }
            btn_cos_acos.setOnClickListener { calc.handleOperationOnFormula(ARCCOS); checkHaptic(it) }
            btn_tan_atan.setOnClickListener { calc.handleOperationOnFormula(ARCTANGENT); checkHaptic(it) }
            btn_log_ceil.setOnClickListener { calc.handleOperationOnFormula(CEILING); checkHaptic(it) }
            btn_ln_floor.setOnClickListener { calc.handleOperationOnFormula(FLOOR); checkHaptic(it) }
            btn_e_neg.setOnClickListener { calc.handleOperationsOnResult(NEGATION); checkHaptic(it) }
            btn_reciprocal_round.setOnClickListener { calc.handleOperationOnFormula(ROUNDING); checkHaptic(it) }
        }
        else {
            btn_shift.setBackgroundResource(R.drawable.shiftbtn)
            btn_pi_rand.setBackgroundResource(R.drawable.pibtn)
            btn_sin_asin.setBackgroundResource(R.drawable.sinbtn)
            btn_cos_acos.setBackgroundResource(R.drawable.cosbtn)
            btn_tan_atan.setBackgroundResource(R.drawable.tanbtn)
            btn_reciprocal_round.setBackgroundResource(R.drawable.invbtn)
            btn_log_ceil.setBackgroundResource(R.drawable.logbtn)
            btn_root_square.setBackgroundResource(R.drawable.sqrbtn)
            btn_mod_cube.setBackgroundResource(R.drawable.modbtn)
            btn_power_abs.setBackgroundResource(R.drawable.powerbtn)
            btn_e_neg.setBackgroundResource(R.drawable.ebtn)
            btn_ln_floor.setBackgroundResource(R.drawable.lnbtn)
            btn_mod_cube.setOnClickListener { calc.handleOperationOnFormula(MODULO); checkHaptic(it) }
            btn_power_abs.setOnClickListener { calc.handleOperationOnFormula(POWER); checkHaptic(it) }
            btn_root_square.setOnClickListener { calc.handleOperationOnFormula(ROOT); checkHaptic(it) }
            btn_pi_rand.setOnClickListener { calc.handleOperationOnFormula(PI); checkHaptic(it) }
            btn_sin_asin.setOnClickListener { calc.handleOperationOnFormula(SINE); checkHaptic(it) }
            btn_cos_acos.setOnClickListener { calc.handleOperationOnFormula(COSINE); checkHaptic(it) }
            btn_tan_atan.setOnClickListener { calc.handleOperationOnFormula(TANGENT); checkHaptic(it) }
            btn_log_ceil.setOnClickListener { calc.handleOperationOnFormula(LOGARITHM); checkHaptic(it) }
            btn_ln_floor.setOnClickListener { calc.handleOperationOnFormula(NATURAL_LOGARITHM); checkHaptic(it) }
            btn_e_neg.setOnClickListener { calc.handleOperationOnFormula(E); checkHaptic(it) }
            btn_reciprocal_round.setOnClickListener { calc.handleOperationsOnResult(RECIPROCAL); checkHaptic(it) }
        }
    }

    override fun setValue(value: String, context: Context) {
        result.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        calc.setValue(Formatter.doubleToString(d))
    }

    override fun setFormula(value: String, context: Context) {
        val input = formula.text.toString() + value
        formula.text = input

        if (value == "")
            formula.text = ""
    }

    override fun getFormula(): String {
        return formula.text.toString()
    }

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.value
        if (copyResult) {
            value = result.value
        }
        if (!value.isEmpty()){
            val clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copied value", value.toString().replace(",",""))
            clipboard.primaryClip = clip
            Toast.makeText(activity!!.applicationContext,"Copied to clipboard", Toast.LENGTH_LONG).show()
        }
        return !value.isEmpty()
    }

    private fun pasteFromClipBoard(): Boolean {
        val clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipboardValue = clipboard.primaryClip.getItemAt(0).coerceToText(activity!!.applicationContext).toString()

        if (clipboardValue.toDoubleOrNull() != null) {
            if (!clipboardValue.toDouble().isNaN()) {
                setFormula(clipboardValue, activity!!.applicationContext)
                Toast.makeText(activity!!.applicationContext, "Pasted from clipboard", Toast.LENGTH_LONG).show()
                calc.calculateResult(getFormula())
                calc.listOfInputLengths.add(clipboardValue.length)
                return true
            }
    }
        return false
    }

    private fun getDigitIds() = listOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5,
            btn_6, btn_7, btn_8, btn_9)


//    //TODO: Fix this, doesn't import from com.simplemobiletools.calculator.extensions.updateViewColors
//    fun Context.updateViewColors(viewGroup: ViewGroup, textColor: Int) {
//        val cnt = viewGroup.childCount
//        (0 until cnt).map { viewGroup.getChildAt(it) }
//                .forEach {
//                    when (it) {
//                        is TextView -> it.setTextColor(textColor)
//                        is Button -> it.setTextColor(textColor)
//                        is ViewGroup -> updateViewColors(it, textColor)
//                    }
//                }
//    }


}
