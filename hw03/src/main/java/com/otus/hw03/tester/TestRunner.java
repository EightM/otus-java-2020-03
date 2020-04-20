package com.otus.hw03.tester;

import com.otus.hw03.annotations.After;
import com.otus.hw03.annotations.Before;
import com.otus.hw03.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class TestRunner {

    private TestRunner() {}

    public static TestResult runTests(Class<?> testClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var classMethods = testClass.getMethods();
        var beforeMethods = new ArrayList<Method>();
        var testMethods = new ArrayList<Method>();
        var afterMethods = new ArrayList<Method>();
        var failedTests = new HashMap<String, String>();

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
            } catch (InvocationTargetException e) {
                failedTests.put(testMethod.getName(), e.getTargetException().getMessage());
            }

            for (Method method : afterMethods) {
                method.invoke(obj);
            }
        }

        return new TestResult(testMethods.size(), failedTests);
    }
}
