package com.simplemobiletools.calculator.helpers;

public class BinaryCalculator {

    //constructors
    public  BinaryCalculator(){}

    //methods
    public String addBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal + secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String subtractBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal - secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String multiplyBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal * secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String divideBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal / secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String convertBinary(String binaryNumber){
        int numberToDecimal = Integer.parseInt(binaryNumber, 2);
        return Integer.toString(numberToDecimal);
    }
}
