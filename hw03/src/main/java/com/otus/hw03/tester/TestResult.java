package com.otus.hw03.tester;

import java.util.Map;

public class TestResult {
    private final int numberOfTests;
    private final Map<String, String> failedTests;

    public TestResult(int numberOfTests, Map<String, String> failedTests) {
        this.numberOfTests = numberOfTests;
        this.failedTests = failedTests;
    }

    @Override
    public String toString() {
        return getGeneralResult() + getDetailedResult();
    }

    public String getDetailedResult() {
        StringBuilder detailedResult = new StringBuilder();
        failedTests.forEach((k, v) -> {
            detailedResult.append(k);
            detailedResult.append(" : ");
            detailedResult.append(v);
            detailedResult.append("\n");
        });

        return detailedResult.toString();
    }

    public String getGeneralResult() {
        return String.format("Tests: %d\n"
                + "Successful: %d\n"
                + "Failed: %d\n", numberOfTests, numberOfTests - failedTests.size(), failedTests.size());
    }
}
