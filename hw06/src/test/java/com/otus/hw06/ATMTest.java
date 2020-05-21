package com.otus.hw06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {
    private final Map<Banknotes, Integer> initMoney = new EnumMap<>(Banknotes.class);

    @BeforeEach
    void initMoney() {
        initMoney.put(Banknotes.THOUSAND, 1);
        initMoney.put(Banknotes.FIFTY, 10);
    }

    @Test
    public void createATMTest() {
        ATM testAtm = new ATM();
        testAtm.putMoney(initMoney);
        assertNotNull(testAtm);
    }

    @Test
    public void getInitBalanceTest() {
        ATM testAtm = new ATM();
        testAtm.putMoney(initMoney);
        assertEquals(1500L, testAtm.getBalance());
    }

    @Test
    public void getBalanceAfterCashWithdraw() {
        ATM testAtm = new ATM();
        testAtm.putMoney(initMoney);
        testAtm.getMoney(1500);
        assertEquals(0, testAtm.getBalance());
    }

    @Test
    public void putMoney() {
        ATM testAtm = new ATM();
        testAtm.putMoney(initMoney);
        Map<Banknotes, Integer> newMoney = new EnumMap<>(Banknotes.class);
        newMoney.put(Banknotes.FIVE_THOUSAND, 3);
        newMoney.put(Banknotes.THOUSAND, 4);
        newMoney.put(Banknotes.FIVE_HUNDRED, 1);
        testAtm.putMoney(newMoney);
        assertEquals(21000, testAtm.getBalance());
    }

    @Test
    public void failedGetMoney() {
        ATM testAtm = new ATM();
        testAtm.putMoney(initMoney);
        testAtm.getMoney(150_000);
        assertEquals(1500, testAtm.getBalance());
    }
}
