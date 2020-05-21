package com.otus.hw07.interfaces;

import com.otus.hw07.Banknotes;

import java.util.Map;

public interface CashAcceptance {
    void putMoney(Map<Banknotes, Integer> money);
}
