package com.simplemobiletools.calculator;

import com.simplemobiletools.calculator.helpers.BinaryCalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryCalculatorTest {

    private BinaryCalculator bc = new BinaryCalculator();

    @Test
    public void addBinaryTest() {
        assertEquals("10011", bc.addBinary("1110", "101"));
    }

    @Test
    public void subtractBinaryTest() {
        assertEquals("11111111111111111111111111111111", bc.subtractBinary("10", "11"));
    }

    @Test
    public void multiplyBinaryTest() {
        assertEquals("1111", bc.multiplyBinary("101", "11"));
    }

    @Test
    public void divideBinaryTest() {
        assertEquals("0", bc.divideBinary("10", "11"));
    }

    @Test
    public void convertBinaryTest() {
        assertEquals("3927", bc.convertBinary("111101010111"));
    }
}