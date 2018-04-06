package com.simplemobiletools.calculator.helpers;

import java.math.BigInteger;

public class BinaryCalculator {

    //constructors
    public  BinaryCalculator(){}

    //methods

    public String andBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal & secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String orBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal | secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String xorBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal ^ secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String norBinary(String first) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int answer = ~firstToDecimal;
        return Integer.toBinaryString(answer);
    }


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
        if(answer < 0){
            return "";
        }else{
            return Integer.toBinaryString(answer);
        }

    }

    public String multiplyBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        BigInteger answer = BigInteger.valueOf(firstToDecimal * secondToDecimal);
        if(answer.compareTo(BigInteger.ZERO) <= 0)
            return "Overflow";
        else
            return answer.toString(2);
    }

    public String divideBinary(String first, String second) {
        int firstToDecimal = Integer.parseInt(first, 2);
        int secondToDecimal = Integer.parseInt(second, 2);
        int answer = firstToDecimal / secondToDecimal;
        return Integer.toBinaryString(answer);
    }

    public String convertBinary(String binaryNumber){
        if(binaryNumber != null && !binaryNumber.equals("Overflow")){
            int numberToDecimal = Integer.parseInt(binaryNumber, 2);
            return Integer.toString(numberToDecimal);
        }
        else if(binaryNumber.equals("Overflow"))
            return "Overflow";
        else{
            return "0";
        }

    }

}
