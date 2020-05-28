package com.otus.hw07;

import com.otus.hw07.interfaces.ATMActions;
import com.otus.hw07.interfaces.CashAcceptance;
import com.otus.hw07.interfaces.CashWithdrawal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ATM implements CashAcceptance, CashWithdrawal, ATMActions {
    private Map<Banknotes, BanknotesCell> cellsSafe;
    private ATMSnapshot initialState = null;

    public ATM() {
        cellsSafe = new EnumMap<>(Banknotes.class);
        cellsSafe.put(Banknotes.FIVE_THOUSAND, new BanknotesCell(Banknotes.FIVE_THOUSAND));
        cellsSafe.put(Banknotes.THOUSAND, new BanknotesCell(Banknotes.THOUSAND));
        cellsSafe.put(Banknotes.FIVE_HUNDRED, new BanknotesCell(Banknotes.FIVE_HUNDRED));
        cellsSafe.put(Banknotes.HUNDRED, new BanknotesCell(Banknotes.HUNDRED));
        cellsSafe.put(Banknotes.FIFTY, new BanknotesCell(Banknotes.FIFTY));
    }

    public void reset() {
        cellsSafe = initialState.getCellsSafeState();
    }

    public void saveInitialState() {
        if (initialState == null) {
            initialState = new ATMSnapshot(cellsSafe);
        } else {
            throw new IllegalStateException();
        }
    }

    public long getBalance() {
        return cellsSafe.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getNumericalValue() * entry.getValue().getQuantity())
                .sum();
    }

    public void printBalance() {
        System.out.printf("Current balance: %d%n", getBalance());
    }

    public Map<Banknotes, Integer> getBanknotesBalance() {
        Map<Banknotes, Integer> banknotesBalance = new EnumMap<>(Banknotes.class);
        cellsSafe.forEach((k, v) -> banknotesBalance.merge(k, v.getQuantity(), Integer::sum));
        return banknotesBalance;
    }

    public void printBanknotesBalance() {
        getBanknotesBalance().forEach((k, v) -> System.out.println(String.format("%s : %d",
                k, v)));
    }

    @Override
    public void putMoney(Map<Banknotes, Integer> money) {
        Objects.requireNonNull(money);
        money.forEach((k, v) -> {
            var currentCell = cellsSafe.get(k);
            currentCell.addToCell(v);
        });
    }

    @Override
    public void getMoney(int requiredAmount) {
        long currentBalance = getBalance();
        if (currentBalance < requiredAmount) {
            System.out.printf("Don't have %d money, maximum: %d%n", requiredAmount, currentBalance);
            return;
        }

        Map<Banknotes, Integer> neededBanknotes = new EnumMap<>(Banknotes.class);

        for (var banknote : Banknotes.values()) {
            int currentNeed = requiredAmount / banknote.getNumericalValue();
            if (currentNeed == 0) {
                continue;
            }

            int currentHave = cellsSafe.get(banknote).getQuantity();
            if (currentHave < currentNeed) {
                neededBanknotes.put(banknote, currentHave);
                requiredAmount -= banknote.getNumericalValue() * currentHave;
            } else {
                neededBanknotes.put(banknote, currentNeed);
                requiredAmount -= banknote.getNumericalValue() * currentNeed;
            }

        }

        neededBanknotes.forEach((k, v) -> {
            var currentCell = cellsSafe.get(k);
            currentCell.extractFromCell(v);
        });
    }

}
