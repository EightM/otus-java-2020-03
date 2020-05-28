package com.otus.hw07;

import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Banknotes, Integer> initMoney = new EnumMap<>(Banknotes.class);
        initMoney.put(Banknotes.FIVE_THOUSAND, 2);
        initMoney.put(Banknotes.THOUSAND, 10);
        initMoney.put(Banknotes.FIVE_HUNDRED, 1);
        initMoney.put(Banknotes.HUNDRED, 5);

        Map<Banknotes, Integer> secondInitMoney = new EnumMap<>(Banknotes.class);
        secondInitMoney.put(Banknotes.FIVE_THOUSAND, 5);

        ATM atm = new ATM();
        ATM atm2 = new ATM();
        atm.putMoney(initMoney);
        atm2.putMoney(secondInitMoney);
        atm.saveInitialState();
        atm2.saveInitialState();
        System.out.println("===Init Department state ===");
        ATMDepartment atmDepartment = new ATMDepartment();
        atmDepartment.addAtm(atm);
        atmDepartment.addAtm(atm2);
        atmDepartment.printBalance();
        atmDepartment.printBanknotesBalance();

        atm.getMoney(10000);
        System.out.println("=== Department state after getting money ===");
        atmDepartment.printBalance();
        atmDepartment.printBanknotesBalance();
        System.out.println();

        atmDepartment.reset();
        System.out.println("=== Department state after reset ===");
        atmDepartment.printBalance();
        atmDepartment.printBanknotesBalance();
    }
}
