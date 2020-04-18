package com.otus.hw03;

public class BankDepositTest {

    private BankDeposit firstTestDeposit;

    @Before
    public void createFirstDeposit() {
        firstTestDeposit = new BankDeposit(1000);
    }

    @Test
    public void testDepositBalance() {
        firstTestDeposit.addMoney(1000);
        if (firstTestDeposit.getBalance() != 2000) {
            throw new ArithmeticException();
        }
    }

    @Test
    public void testZeroDeposit() {
        firstTestDeposit.takeMoney(1000);
        if (firstTestDeposit.getBalance() != 0) {
            throw new ArithmeticException();
        }
    }

    @Test
    public void testSetDeposit() {
        firstTestDeposit.setBalance(1_000_000);
        if (firstTestDeposit.getBalance() != 1_000_000) {
            throw new ArithmeticException();
        }
    }

    @After
    public void ClearDeposit() {
        firstTestDeposit = null;
    }
}
