package com.otus.hw07.interfaces;

import com.otus.hw07.Banknotes;

import java.util.Map;

public interface ATMActions {
    void printBalance();
    void printBanknotesBalance();
    long getBalance();
    void reset();
    Map<Banknotes, Integer> getBanknotesBalance();
}
