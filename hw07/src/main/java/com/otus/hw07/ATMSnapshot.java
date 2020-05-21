package com.otus.hw07;

import com.otus.hw07.interfaces.Snapshot;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ATMSnapshot implements Snapshot {
    private final Map<Banknotes, BanknotesCell> cellsSafeState = new EnumMap<>(Banknotes.class);

    public ATMSnapshot(Map<Banknotes, BanknotesCell> cellsSafeState) {
        Objects.requireNonNull(cellsSafeState);
        cellsSafeState.forEach((k, v) -> this.cellsSafeState.put(k, new BanknotesCell(v)));
    }

    public Map<Banknotes, BanknotesCell> getCellsSafeState() {
        return cellsSafeState;
    }
}
