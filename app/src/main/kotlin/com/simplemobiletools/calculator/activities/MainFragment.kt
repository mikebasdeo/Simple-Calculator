package com.simplemobiletools.calculator.activities


import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.simplemobiletools.calculator.R
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.value
import me.grantland.widget.AutofitHelper
import com.simplemobiletools.calculator.helpers.*
import kotlinx.android.synthetic.main.fragment_1.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), Calculator {

    //ATTRIBUTES

    var storedTextColor = 0
    var vibrateOnButtonPress = true
    var storedUseEnglish = false
    var shiftClicked = false
    lateinit var  calc: CalculatorImpl


   // var calc: CalculatorImpl = CalculatorImpl(this, activity!!.applicationContext)

    //CONSTRUCTORS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
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
            calc.storeHistory(getFormula())
            calc.storeResult(result.text.toString())
        }

        formula.setOnLongClickListener { copyToClipboard(false) }
        formula.setOnLongClickListener{ pasteFromClipBoard()}
        result.setOnLongClickListener { copyToClipboard(true) }

        AutofitHelper.create(result)
        AutofitHelper.create(formula)

        changeButtonFunctionality(shiftClicked)


//        storeStateVariables()
//        updateViewColors(calculator_holder, config.textColor)






    }

    fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    fun changeButtonFunctionality(shiftClicked: Boolean){
        val mapOfButtonsOnFirstScreen = mapOf<Button, String>(
                btn_pi_rand to "π",
                btn_sin_asin to "SIN",
                btn_cos_acos to "COS",
                btn_tan_atan to "TAN",
                btn_reciprocal_round to "x⁻¹",
                btn_log_ceil to "LOG",
                btn_root_square to "√",
                btn_mod_cube to "MOD",
                btn_power_abs to "^",
                btn_e_neg to "e",
                btn_ln_floor to "LN")
        val mapOfButtonsOnSecondScreen = mapOf<Button, String>(
                btn_pi_rand to "RAND",
                btn_sin_asin to "ASIN",
                btn_cos_acos to "ACOS",
                btn_tan_atan to "ATAN",
                btn_reciprocal_round to "ROUND",
                btn_log_ceil to "CEIL",
                btn_root_square to "x²",
                btn_mod_cube to "x³",
                btn_power_abs to "ABS",
                btn_e_neg to "±",
                btn_ln_floor to "FLOOR")

        if(shiftClicked){
            btn_shift.setTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_5))
            btn_shift.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_4))
            btn_pi_rand.textSize = 18f
            for(m in mapOfButtonsOnSecondScreen){
                m.key.text = m.value
                m.key.setTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_4))
                m.key.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_5))
            }
            btn_mod_cube.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.CUBE); checkHaptic(it) }
            btn_power_abs.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ABSOLUTE_VALUE); checkHaptic(it) }
            btn_root_square.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.SQUARE); checkHaptic(it) }
            btn_pi_rand.setOnClickListener { calc.handleOperationsOnResult(CONSTANT.RANDOM); checkHaptic(it) }
            btn_sin_asin.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ARCSINE); checkHaptic(it) }
            btn_cos_acos.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ARCCOS); checkHaptic(it) }
            btn_tan_atan.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ARCTANGENT); checkHaptic(it) }
            btn_log_ceil.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.CEILING); checkHaptic(it) }
            btn_ln_floor.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.FLOOR); checkHaptic(it) }
            btn_e_neg.setOnClickListener { calc.handleOperationsOnResult(CONSTANT.NEGATION); checkHaptic(it) }
            btn_reciprocal_round.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ROUNDING); checkHaptic(it) }
        }
        else {
            btn_shift.setTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_4))
            btn_shift.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_5))
            btn_pi_rand.textSize = 20f
            for (m in mapOfButtonsOnFirstScreen) {
                m.key.text = m.value
                m.key.setTextColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_5))
                m.key.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.noah_4))
            }
            btn_mod_cube.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.MODULO); checkHaptic(it) }
            btn_power_abs.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.POWER); checkHaptic(it) }
            btn_root_square.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.ROOT); checkHaptic(it) }
            btn_pi_rand.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.PI); checkHaptic(it) }
            btn_sin_asin.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.SINE); checkHaptic(it) }
            btn_cos_acos.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.COSINE); checkHaptic(it) }
            btn_tan_atan.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.TANGENT); checkHaptic(it) }
            btn_log_ceil.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.LOGARITHM); checkHaptic(it) }
            btn_ln_floor.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.NATURAL_LOGARITHM); checkHaptic(it) }
            btn_e_neg.setOnClickListener { calc.handleOperationOnFormula(CONSTANT.E); checkHaptic(it) }
            btn_reciprocal_round.setOnClickListener { calc.handleOperationsOnResult(CONSTANT.RECIPROCAL); checkHaptic(it) }
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

        return if (value.isEmpty()) {
            false
        } else {
            //copyToClipboard(value)
            true
        }
    }

    private fun pasteFromClipBoard(): Boolean {
        //check clipboard
        val clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return if (clipboard.primaryClip.getItemAt(0).coerceToText(activity!!.applicationContext).toString().isNum()){
            setFormula(clipboard.primaryClip.getItemAt(0).coerceToText(activity!!.applicationContext).toString(), activity!!.applicationContext)
            Toast.makeText(activity!!.applicationContext,"Pasted from clipboard", Toast.LENGTH_LONG).show()
            true
        }
        else {
            //do nothing
            false
        }
    }

    private fun String.isNum() = matches(Regex("\\d|\\d{2}|\\d{3}(\\d{3},)+(.|)(\\d)+"))

    private fun getDigitIds() = listOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5,
            btn_6, btn_7, btn_8, btn_9)


    //TODO: Fix this, doesn't import from com.simplemobiletools.calculator.extensions.updateViewColors
    fun Context.updateViewColors(viewGroup: ViewGroup, textColor: Int) {
        val cnt = viewGroup.childCount
        (0 until cnt).map { viewGroup.getChildAt(it) }
                .forEach {
                    when (it) {
                        is TextView -> it.setTextColor(textColor)
                        is Button -> it.setTextColor(textColor)
                        is ViewGroup -> updateViewColors(it, textColor)
                    }
                }
    }

    val Context.config: Config get() = Config.newInstance(applicationContext)

//    private fun storeStateVariables() {
//        con.apply {
//            storedTextColor = textColor
//            storedUseEnglish = useEnglish
//        }
//    }
}// Required empty public constructor
