package com.otus.hw06;

import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Banknotes, BanknotesCell> initMoney = new EnumMap<>(Banknotes.class);
        initMoney.put(Banknotes.FIVE_THOUSAND, new BanknotesCell(2));
        initMoney.put(Banknotes.THOUSAND, new BanknotesCell(10));
        initMoney.put(Banknotes.FIVE_HUNDRED, new BanknotesCell(1));
        initMoney.put(Banknotes.HUNDRED, new BanknotesCell(5));
        initMoney.put(Banknotes.FIFTY, new BanknotesCell(0));

        ATM atm = new ATM(initMoney);
        System.out.println("=== Init state ===");
        atm.printBanknotesBalance();
        atm.getMoney(16500);
        System.out.println("=== After get money");
        atm.printBanknotesBalance();
    }
}
