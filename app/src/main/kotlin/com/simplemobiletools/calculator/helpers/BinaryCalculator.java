package com.simplemobiletools.calculator.helpers;

public class BinaryCalculator {

    //attributes
    private String firstNumber;
    private String secondNumber;
    private String answer;

    //constructors
    public  BinaryCalculator(){}

    //methods
    public String addBinary(String first, String second){
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal + secondToDecimal;
        return Integer.toBinaryString(answer);
    }
}
