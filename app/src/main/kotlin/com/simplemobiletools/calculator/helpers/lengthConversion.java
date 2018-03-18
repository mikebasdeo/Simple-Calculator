package com.simplemobiletools.calculator.helpers;
import java.lang.reflect.Array;
import java.text.NumberFormat;

import static com.simplemobiletools.calculator.helpers.CONSTANT.CENTIMETERS;
import static com.simplemobiletools.calculator.helpers.CONSTANT.FEET;
import static com.simplemobiletools.calculator.helpers.CONSTANT.INCHES;
import static com.simplemobiletools.calculator.helpers.CONSTANT.KILOMETERS;
import static com.simplemobiletools.calculator.helpers.CONSTANT.METERS;
import static com.simplemobiletools.calculator.helpers.CONSTANT.MILES;
import static com.simplemobiletools.calculator.helpers.CONSTANT.MILLIMETERS;
import static com.simplemobiletools.calculator.helpers.CONSTANT.YARDS;

/**
 * Created by leban on 2018-03-15.
 */

public class lengthConversion {

    //Constant values for comparisons
    final double meters =1;
    final double centimeters = 100;
    final double milimeters = 1000;
    final double kilometers = 0.001;
    final double feet = 3.281;
    final double inches = 39.37;
    final double miles = 0.0006214;
    final double yards = 1.093613;

    //inputs and outputs
    private double beginning_qty;
    private double ending_qty;
    private String beginning_unit_type;
    private String ending_unit_type;
    private String[] conversionChoiceList = {"Speed", "Distance", "Time", "Weight"};

    //constructor
    public lengthConversion(){
        beginning_qty = 0;
        ending_qty = 0;
        beginning_unit_type = "";
        ending_unit_type = "";
    }

    //GETTERS AND SETTERS
    public String[] getConversionChoiceList(){
        return conversionChoiceList;
    }
    public String[] getSpeedUnitsList(){
         String[] unitsList = {"Velocity", "Acceleration", "Displacement"};
         return unitsList;
    }
    public String[] getDistanceUnitsList(){
        String[] unitsList = {CENTIMETERS, MILLIMETERS, METERS, KILOMETERS, FEET, INCHES, MILES, YARDS};
        return unitsList;
    }

    public String[] getWeightUnitsList(){
        String[] unitsList = {"Pounds", "Ounces"};
        return unitsList;
    }
    public String[] getTimeUnitsList(){
        String[] unitsList = {"Hours", "Minutes", "Seconds"};
        return unitsList;
    }

    public double getMeters() {
        return meters;
    }

    public double getCentimeters() {
        return centimeters;
    }

    public double getMilimeters() {
        return milimeters;
    }

    public double getKilometers() {
        return kilometers;
    }

    public double getFeet() {
        return feet;
    }

    public double getInches() {
        return inches;
    }

    public double getMiles() {
        return miles;
    }

    public double getYards() {
        return yards;
    }

    public double getBeginning_qty() {
        return beginning_qty;
    }

    public void setBeginning_qty(double beginning_qty) {
        this.beginning_qty = beginning_qty;
    }

    public double getEnding_qty() {
        return ending_qty;
    }

    public void setEnding_qty(double ending_qty) {
        this.ending_qty = ending_qty;
    }

    public String getBeginning_unit_type() {
        return beginning_unit_type;
    }

    public void setBeginning_unit_type(String beginning_unit_type) {
        this.beginning_unit_type = beginning_unit_type;
    }

    public String getEnding_unit_type() {
        return ending_unit_type;
    }

    public void setEnding_unit_type(String ending_unit_type) {
        this.ending_unit_type = ending_unit_type;
    }

    public double getUnitTypeConstant(String unit_type){
        if(unit_type == METERS){return meters;}
        if(unit_type == CENTIMETERS){return centimeters;}
        if(unit_type == MILLIMETERS){return milimeters;}
        if(unit_type == KILOMETERS){return kilometers;}
        if(unit_type == INCHES){return inches;}
        if(unit_type == FEET){return feet;}
        if(unit_type == YARDS){return yards;}
        return 0;
    }

    public double calculateEnding_qty(){
        double beginning_qty = getBeginning_qty();
        double ending_qty = getEnding_qty();
        double beginning_unit_type = getUnitTypeConstant(getBeginning_unit_type());
        double ending_unit_type = getUnitTypeConstant(getEnding_unit_type());

        //convert to meters
        ending_qty = meters/beginning_unit_type;

        //multiply by initial quantity
        ending_qty = ending_qty * ending_unit_type;

        //multiply by initial quantity to get final quantity
        ending_qty = ending_qty * beginning_qty;

        return ending_qty;
    }

    public String toString(){
        NumberFormat nf = NumberFormat.getNumberInstance();

        if(ending_unit_type.equals("inches")||ending_unit_type.equals("feet")||ending_unit_type.equals("meters")||ending_unit_type.equals("centimeters")||ending_unit_type.equals("milimeters")||ending_unit_type.equals("miles")||ending_unit_type.equals("yards")){
            nf.setMaximumFractionDigits(4);
        }
        else{
            nf.setMaximumFractionDigits(6);
        }
        return nf.format(getEnding_qty()) + " " + getEnding_unit_type();

    }


}
