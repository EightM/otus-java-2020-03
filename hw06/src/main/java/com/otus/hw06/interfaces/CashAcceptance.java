package com.otus.hw06.interfaces;

import com.otus.hw06.Banknotes;

import java.util.Map;

public interface CashAcceptance {
    void putMoney(Map<Banknotes, Integer> money);
}
