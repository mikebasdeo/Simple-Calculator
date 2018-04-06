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
        assertEquals("0", bc.subtractBinary("1", "1"));
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

    @Test
    public void andBinaryTest(){assertEquals("1100", bc.andBinary("1100", "1100"));}

    @Test
    public void orBinaryTest(){assertEquals("1011", bc.andBinary("1011", "1000"));}

    @Test
    public void xorBinaryTest(){assertEquals("11", bc.andBinary("1011", "1000"));}


}