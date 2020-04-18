package com.otus.hw03;

public class BankDeposit {

    private double balance;

    public BankDeposit(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double addMoney(double money) {
        this.balance += money;
        return this.balance;
    }

    public double takeMoney(double money) {
        this.balance -= money;
        return balance;
    }

}
