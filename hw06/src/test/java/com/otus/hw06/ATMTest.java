package com.otus.hw06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {
    private final Map<Banknotes, BanknotesCell> initMoney = new EnumMap<>(Banknotes.class);

    @BeforeEach
    void initMoney() {
        initMoney.put(Banknotes.FIVE_THOUSAND, new BanknotesCell(0));
        initMoney.put(Banknotes.THOUSAND, new BanknotesCell(1));
        initMoney.put(Banknotes.FIVE_HUNDRED, new BanknotesCell(0));
        initMoney.put(Banknotes.HUNDRED, new BanknotesCell(0));
        initMoney.put(Banknotes.FIFTY, new BanknotesCell(10));
    }

    @Test
    public void createATMTest() {
        ATM testAtm = new ATM(initMoney);
        assertNotNull(testAtm);
    }

    @Test
    public void getInitBalanceTest() {
        ATM testAtm = new ATM(initMoney);
        assertEquals(1500L, testAtm.getBalance());
    }

    @Test
    public void getBalanceAfterCashWithdraw() {
        ATM testAtm = new ATM(initMoney);
        testAtm.getMoney(1500);
        assertEquals(0, testAtm.getBalance());
    }

    @Test
    public void putMoney() {
        ATM testAtm = new ATM(initMoney);
        Map<Banknotes, BanknotesCell> newMoney = new EnumMap<>(Banknotes.class);
        newMoney.put(Banknotes.FIVE_THOUSAND, new BanknotesCell(3));
        newMoney.put(Banknotes.THOUSAND, new BanknotesCell(4));
        newMoney.put(Banknotes.FIVE_HUNDRED, new BanknotesCell(1));
        testAtm.putMoney(newMoney);
        assertEquals(21000, testAtm.getBalance());
    }

    @Test
    public void failedGetMoney() {
        ATM testAtm = new ATM(initMoney);
        testAtm.getMoney(150_000);
        assertEquals(1500, testAtm.getBalance());
    }
}
