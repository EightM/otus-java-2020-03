package com.otus.hw03;

import com.otus.hw03.annotations.After;
import com.otus.hw03.annotations.Before;
import com.otus.hw03.annotations.Test;

public class BankDepositTest {

    private BankDeposit firstTestDeposit;

    @Before
    public void createFirstDeposit() {
        firstTestDeposit = new BankDeposit(1000);
    }

    @Test
    public void testDepositBalance() {
        firstTestDeposit.addMoney(1001.0);
        double rightBalance = 2000.0;
        if (firstTestDeposit.getBalance() != rightBalance) {
            String errorMessage = String.format("Expected getBalance %.1f, but was %.1f",
                    rightBalance, firstTestDeposit.getBalance());
            throw new ArithmeticException(errorMessage);
        }
    }

    @Test
    public void testZeroDeposit() {
        firstTestDeposit.takeMoney(1000.0);
        double rightBalance = 0.0;
        if (firstTestDeposit.getBalance() != rightBalance) {
            String errorMessage = String.format("Expected getBalance %.1f, but was %.1f",
                    rightBalance, firstTestDeposit.getBalance());
            throw new ArithmeticException(errorMessage);
        }
    }

    @Test
    public void testSetDeposit() {
        firstTestDeposit.setBalance(1_000_001.0);
        double rightBalance = 1_000_000.0;
        if (firstTestDeposit.getBalance() != rightBalance) {
            String errorMessage = String.format("Expected getBalance %.1f, but was %.1f",
                    rightBalance, firstTestDeposit.getBalance());
            throw new ArithmeticException(errorMessage);
        }
    }

    @After
    public void ClearDeposit() {
        firstTestDeposit = null;
    }
}
