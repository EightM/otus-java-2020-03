package com.otus.hw05;

import com.otus.hw05.annotations.Log;

public class Calculator {

    @Log
    public static int calculateSum(int a, int b) {
        return a + b;
    }

    @Log
    public static double calculateDivide(double a, double c) {
        return a / c;
    }

    @Log
    public static long calculateMultiply(long a, int b) {
        return a * b;
    }

    public static int calculateSubtract(int a, int b) {
        return a - b;
    }

}
