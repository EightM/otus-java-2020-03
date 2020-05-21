package com.otus.hw07;

import com.otus.hw07.interfaces.ATMActions;

import java.util.*;

public class ATMDepartment implements ATMActions {
    private final Set<ATM> machines;

    public ATMDepartment() {
        this.machines = new HashSet<>();
    }

    public void addAtm(ATM atm) {
        machines.add(atm);
    }

    public boolean removeAtm(ATM atm) {
        return machines.remove(atm);
    }

    @Override
    public void printBalance() {
        System.out.println("Department balance: " + getBalance());
    }

    @Override
    public long getBalance() {
        return machines.stream()
                .map(ATM::getBalance).reduce(0L, Long::sum);
    }

    @Override
    public void printBanknotesBalance() {
        getBanknotesBalance().forEach((k, v) -> System.out.println(String.format("%s : %d",
                k, v)));
    }

    @Override
    public Map<Banknotes, Integer> getBanknotesBalance() {
        Map<Banknotes, Integer> banknotesBalance = new EnumMap<>(Banknotes.class);
        machines.stream()
                .map(ATM::getBanknotesBalance)
                .forEach(banknoteBalance -> banknoteBalance
                        .forEach((k, v) -> banknotesBalance.merge(k, v, Integer::sum)));

        return banknotesBalance;
    }

    @Override
    public void reset() {
        machines.forEach(ATM::reset);
    }
}
