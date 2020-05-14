package com.otus.hw06;

public enum Banknotes {
    FIVE_THOUSAND(5000), THOUSAND(1000), FIVE_HUNDRED(500), HUNDRED(100), FIFTY(50);

    private final int numericalValue;

    Banknotes(int i) {
        numericalValue = i;
    }

    public int getNumericalValue() {
        return numericalValue;
    }
}
