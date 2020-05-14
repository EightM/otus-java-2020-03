package com.otus.hw06;

import com.otus.hw06.interfaces.CashAcceptance;
import com.otus.hw06.interfaces.CashWithdrawal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ATM implements CashAcceptance, CashWithdrawal {
    private final Map<Banknotes, Integer> safe;


    public ATM(Map<Banknotes, Integer> safe) {
        Objects.requireNonNull(safe);
        this.safe = new EnumMap<>(safe);
    }

    public long getBalance() {
        return safe.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getNumericalValue() * entry.getValue())
                .sum();
    }

    public void printBalance() {
        System.out.printf("Current balance: %d%n", getBalance());
    }

    public void printBanknotesBalance() {
        System.out.println(safe);
    }

    @Override
    public void putMoney(Map<Banknotes, Integer> money) {
        Objects.requireNonNull(money);

        money.forEach((k, v) -> safe.merge(k, v, Integer::sum));
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

            Integer currentHave = safe.getOrDefault(banknote, 0);
            if (currentHave < currentNeed) {
                neededBanknotes.put(banknote, currentHave);
                requiredAmount -= banknote.getNumericalValue() * currentHave;
            } else {
                neededBanknotes.put(banknote, currentNeed);
                requiredAmount -= banknote.getNumericalValue() * currentNeed;
            }

        }

        neededBanknotes.forEach((k, v) -> safe.merge(k, v, (a, b) -> a - b));
    }

}
