package com.otus.hw03;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            runTests(BankDepositTest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runTests(Class<?> testClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var classMethods = testClass.getMethods();
        var beforeMethods = new ArrayList<Method>();
        var testMethods = new ArrayList<Method>();
        var afterMethods = new ArrayList<Method>();
        int failedTests = 0;

        for (Method method : classMethods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }

        for (Method testMethod : testMethods) {
            var obj = testClass.getConstructor().newInstance();

            for (Method method : beforeMethods) {
                method.invoke(obj);
            }

            try {
                testMethod.invoke(obj);
            } catch (Exception e) {
                failedTests++;
            }

            for (Method method : afterMethods) {
                method.invoke(obj);
            }
        }

        System.out.println("Tests: " + testMethods.size());
        System.out.println("Successful: " + (testMethods.size() - failedTests));
        System.out.println("Failed: " + failedTests);
    }
}
