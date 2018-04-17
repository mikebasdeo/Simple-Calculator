package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.commons.activities.AboutActivity
import com.simplemobiletools.commons.extensions.baseConfig
import com.simplemobiletools.commons.extensions.getInternalStoragePath
import com.simplemobiletools.commons.extensions.updateSDCardPath
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : AppCompatActivity() {

    var storedTextColor = 0
    var storedUseEnglish = false
    private var vibrateOnButtonPress = true

    lateinit var toolbar : Toolbar
    lateinit var tablayout : TabLayout
    lateinit var viewpager : ViewPager
    lateinit var viewpageradapter : ViewPagerAdapter

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appLaunched()

        //Tabs toolbar crap
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

        vibrateOnButtonPress = config.vibrateOnButtonPress
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

    private fun launchSettings() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }

    private fun launchAbout() {
        startActivity(Intent(applicationContext, AboutActivity::class.java))
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