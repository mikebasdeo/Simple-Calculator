package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.Toolbar
import com.simplemobiletools.calculator.*
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.commons.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {

    var storedTextColor = 0
    var storedUseEnglish = false

    lateinit var toolbar : Toolbar
    lateinit var tablayout : TabLayout
    lateinit var viewpager : ViewPager
    lateinit var viewpageradapter : ViewPagerAdapter

//    private val modifierIds: List<Button> = listOf(btn_pi_rand, btn_sin_asin, btn_cos_acos, btn_tan_atan,
//            btn_reciprocal_round, btn_log_ceil, btn_root_square, btn_mod_cube,
//            btn_power_abs, btn_e_neg, btn_ln_floor, btn_decimal, btn_0, btn_1,
//            btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_del, btn_all_clear, btn_multiply,
//            btn_plus, btn_divide, btn_minus, btn_left_bracket)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched()






        //toolbar crap
        toolbar = toolBar
        setSupportActionBar(toolbar)
        tablayout = tabLayout
        viewpager = viewPager
        viewpageradapter =  ViewPagerAdapter(supportFragmentManager)
        viewpageradapter.addFragments(CalculatorFragment(), "Calculator")
        viewpageradapter.addFragments(UnitConversionFragment(), "Unit Conversion")
        viewpageradapter.addFragments(BinaryCalculatorFragment(), "Binary Conversion")
        viewpager.adapter = viewpageradapter
        tablayout.setupWithViewPager(viewpager)







    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            //restartActivity()
            return
        }

        if (storedTextColor != config.textColor) {
            updateViewColors(calculator_holder, config.textColor)
        }
       // vibrateOnButtonPress = config.vibrateOnButtonPress
    }

    override fun onPause() {
        super.onPause()
        storeStateVariables()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.History -> launchHistory()
            R.id.settings -> launchSettings()
            R.id.about -> launchAbout()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
            storedUseEnglish = useEnglish
        }
    }



    private fun launchHistory() {
        startActivity(Intent(applicationContext, HistoryActivity::class.java))
    }

//    private fun launchUnitConversion(){
//        startActivity(Intent(applicationContext, UnitConversionActivity::class.java))
//    }
//    private fun launchBinaryCalculator(){
//        startActivity(Intent(applicationContext, BinaryCalculatorActivity::class.java))
//    }

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        //startAboutActivity(R.string.app_name, LICENSE_KOTLIN or LICENSE_AUTOFITTEXTVIEW or LICENSE_ROBOLECTRIC or LICENSE_ESPRESSO, BuildConfig.VERSION_NAME)
    }



    @Override
    private fun Activity.appLaunched() {
        baseConfig.internalStoragePath = getInternalStoragePath()
        updateSDCardPath()
        baseConfig.appRunCount++
        //Uncomment and replace values.xml strings if we ever want to put our own donation info
//        if (!isThankYouInstalled() && (baseConfig.appRunCount % 50 == 0)) {
//            DonateDialog(this)
//        }
    }
}