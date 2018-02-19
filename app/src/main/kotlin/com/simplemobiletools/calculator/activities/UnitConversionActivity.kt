package com.simplemobiletools.calculator.activities

import android.os.Bundle
import android.app.Activity
import android.widget.*
import com.simplemobiletools.calculator.R
import android.view.View
class UnitConversionActivity : Activity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)

        val conversionChoice : Spinner = findViewById(R.id.conversion_type) as Spinner
        val conversionListBefore = findViewById(R.id.conversion_list_before) as ListView
        val conversionListAfter = findViewById(R.id.conversion_list_after) as ListView
        val categories= ArrayList<String>()

        categories.add("Area")
        categories.add("Speed")
        categories.add("Temperature")
        categories.add("Time")
        categories.add("Volume")


        conversionChoice.adapter = ArrayAdapter(this, R.layout.spinner_item, categories)

        conversionChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    //Connect to units here.
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }

















}
