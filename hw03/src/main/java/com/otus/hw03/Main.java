package com.otus.hw03;

import com.otus.hw03.tester.TestResult;
import com.otus.hw03.tester.TestRunner;

public class Main {
    public static void main(String[] args) {
        try {
            TestResult testResult = TestRunner.runTests(BankDepositTest.class);
            System.out.println(testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
