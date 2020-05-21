package com.otus.hw06;

import com.otus.hw06.interfaces.Cell;

public class BanknotesCell implements Cell {
    private int quantity;
    private final Banknotes cellBanknoteType;

    public BanknotesCell(Banknotes cellBanknoteType) {
        this.cellBanknoteType = cellBanknoteType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public void addToCell(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }

        this.quantity += quantity;
    }

    @Override
    public void extractFromCell(int quantity) {
        if (quantity < 0 || quantity > this.quantity) {
            throw new IllegalArgumentException();
        }

        this.quantity -= quantity;
    }

    public Banknotes getCellBanknoteType() {
        return cellBanknoteType;
    }
}
