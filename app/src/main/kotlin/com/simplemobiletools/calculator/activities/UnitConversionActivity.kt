package com.simplemobiletools.calculator.activities

import android.os.Bundle
import android.app.Activity
import com.simplemobiletools.calculator.R
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

class UnitConversionActivity : Activity()  {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)

        //Three drop down menus. The conversionChoiceSpiner changes the other two automatically.
        val conversionChoiceSpinner: Spinner
        val unitsBeforeSpinner: Spinner
        val unitsAfterSpinner: Spinner


        //Main List for conversion choices.
        val ConversionChoiceList = arrayOf("Speed", "Distance", "Time", "Weight")

        //Empty list that will be populated with the relevant conversion units.
        val unitList = ArrayList<String>()

        val choiceAdapter: ArrayAdapter<String>
        val beforeAdapter: ArrayAdapter<String>
        val afterAdapter: ArrayAdapter<String>

        //Create adapters for each of the three spinners.
        //TODO: Make custom layouts for the drop down menus.
        choiceAdapter = ArrayAdapter(this, R.layout.spinner_item, ConversionChoiceList)
        beforeAdapter = ArrayAdapter(this, R.layout.spinner_item, unitList)
        afterAdapter = ArrayAdapter(this, R.layout.spinner_item, unitList)

        //Connect to layout.
        conversionChoiceSpinner = findViewById(R.id.conversion_type_spinner) as Spinner
        unitsBeforeSpinner = findViewById(R.id.units_before_spinner) as Spinner
        unitsAfterSpinner = findViewById(R.id.units_after_spinner) as Spinner

        //Connect each spinner to its respective adapter.
        conversionChoiceSpinner.setAdapter(choiceAdapter)
        unitsBeforeSpinner.setAdapter(beforeAdapter)
        unitsAfterSpinner.setAdapter(afterAdapter)

        conversionChoiceSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            //TODO: Add all the Unit choices here. Probably in a switch statement. Or maybe in strings.xml if you can.
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {
                val s = conversionChoiceSpinner.getItemAtPosition(arg2).toString()
                if(s == "Distance"){
                    unitList.clear()
                    unitList.add("Km")
                    unitList.add("m")
                    unitList.add("cm")
                }
                if(s == "Speed"){
                    unitList.clear()
                    unitList.add("Velocity")
                    unitList.add("Acceleration")
                    unitList.add("Displacement")
                }
                if (s == "Weight"){
                    unitList.clear()
                    unitList.add("Kg")
                    unitList.add("Pounds")
                    unitList.add("Newtons")
                }
                beforeAdapter.notifyDataSetChanged()
                afterAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
    }

}
