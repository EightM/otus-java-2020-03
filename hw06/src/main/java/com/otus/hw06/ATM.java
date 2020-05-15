package com.otus.hw06;

import com.otus.hw06.interfaces.CashAcceptance;
import com.otus.hw06.interfaces.CashWithdrawal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ATM implements CashAcceptance, CashWithdrawal {
    private final Map<Banknotes, BanknotesCell> cellsSafe;


    public ATM(Map<Banknotes, BanknotesCell> cellsSafe) {
        Objects.requireNonNull(cellsSafe);
        this.cellsSafe = new EnumMap<>(cellsSafe);
    }

    public long getBalance() {
        return cellsSafe.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getNumericalValue() * entry.getValue().getQuantity())
                .sum();
    }

    public void printBalance() {
        System.out.printf("Current balance: %d%n", getBalance());
    }

    public void printBanknotesBalance() {
        cellsSafe.forEach((k, v) -> System.out.println(String.format("%s : %d",
                k, v.getQuantity())));
    }

    @Override
    public void putMoney(Map<Banknotes, BanknotesCell> money) {
        Objects.requireNonNull(money);

        money.forEach((k, v) -> cellsSafe
                .merge(k, v, (oldCell, newCell) -> new BanknotesCell(oldCell.getQuantity() + newCell.getQuantity())));
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
            currentCell.setQuantity(currentCell.getQuantity() - v);
        });
    }

}
