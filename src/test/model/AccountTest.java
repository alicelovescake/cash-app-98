package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account testAccountA;
    private Account testAccountB;
    private Account testBusinessAccount;
    private User testUserA;
    private User testUserB;
    private BusinessUser testBusinessUser;
    private CreditCard testCard;

    @BeforeEach
    void setUp() {
        testUserA = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testCard = new CreditCard("Visa", 123456, 2025, 12);
        testBusinessUser = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.AMAZON);
        testAccountA = new Account(testUserA, 100.50);
        testBusinessAccount = new Account(testBusinessUser, 5000);
        testUserB = new PersonalUser
                ("$moneymaker", "Toronto", "Bob", "Marley");
        testAccountB = new Account(testUserB, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(testUserA, testAccountA.getUser());
        assertEquals(100.50, testAccountA.getBalance());
        assertTrue(testAccountA.getId().length() > 0);
    }

    @Test
    void testDeposit() {
        testAccountA.addCreditCard(testCard);
        testAccountA.deposit(testCard, 500.50);
        assertEquals(601.0, testAccountA.getBalance());
    }

    void testDepositInvalidCreditCard() {
        CreditCard invalidCard = new CreditCard("Visa", 2324, 1990, 1);
        testAccountA.addCreditCard(invalidCard);
        testAccountA.deposit(invalidCard, 500.50);
        assertEquals(100.50, testAccountA.getBalance());
    }

    @Test
    void testWithdrawSufficientFunds() {
        testAccountA.addCreditCard(testCard);
        assertTrue(testAccountA.withdraw(testCard, 50.50));
        assertEquals(50.0, testAccountA.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        testAccountA.addCreditCard(testCard);
        testAccountA.deposit(testCard, 500.50);
        testAccountA.deposit(testCard, 10.50);
        assertEquals(611.50, testAccountA.getBalance());
    }

    @Test
    void testMultipleWithdrawInsufficientFunds() {
        testAccountA.addCreditCard(testCard);
        assertTrue(testAccountA.withdraw(testCard, 10.00));
        assertEquals(90.50, testAccountA.getBalance());
        assertTrue(testAccountA.withdraw(testCard, 20.00));
        assertEquals(70.50, testAccountA.getBalance());
        assertFalse(testAccountA.withdraw(testCard, 100.00));
        assertEquals(70.50, testAccountA.getBalance());
    }

    @Test
    void testSendMoney() {
        testAccountA.sendMoney(testAccountB, 50);
        //Both accountA and B should have added a new transaction to their respective list
        assertEquals(1, testAccountA.getCompletedTransactions().size());
        assertEquals(1, testAccountB.getCompletedTransactions().size());
        //Balance updated
        assertEquals(50.50, testAccountA.getBalance());
        assertEquals(150, testAccountB.getBalance());
    }

    @Test
    void testSendMoneyMultipleInsufficientFunds(){
        testAccountA.sendMoney(testAccountB, 60.0);
        testAccountA.sendMoney(testAccountB, 200.0);

        assertEquals(1, testAccountA.getCompletedTransactions().size());
        assertEquals(1, testAccountB.getCompletedTransactions().size());
        assertEquals(1, testAccountA.getFailedTransactions().size());
        assertEquals(1, testAccountB.getFailedTransactions().size());
        assertEquals(40.50, testAccountA.getBalance());
        assertEquals(160, testAccountB.getBalance());

    }

    @Test
    void testReceiveMoney() {
        testAccountA.receiveMoney(50.0);
        assertEquals(150.50, testAccountA.getBalance());
        testAccountA.receiveMoney(200.0);
        assertEquals(350.50, testAccountA.getBalance());
    }

    @Test
    void testMakePurchase() {
        testAccountA.makePurchase(testBusinessAccount, 50);
        assertEquals(1, testAccountA.getCompletedTransactions().size());
        assertEquals(50.50, testAccountA.getBalance());
        assertEquals(5050.0, testBusinessAccount.getBalance());
    }

    @Test
    void testMakeMultiplePurchaseInsufficientFunds() {
        testAccountA.makePurchase(testBusinessAccount, 10.00);
        testAccountA.makePurchase(testBusinessAccount, 1000.00);
        assertEquals(1, testAccountA.getCompletedTransactions().size());
        assertEquals(1, testAccountA.getFailedTransactions().size());
        assertEquals(1, testBusinessAccount.getCompletedTransactions().size());
        assertEquals(1, testBusinessAccount.getFailedTransactions().size());
        assertEquals(90.50, testAccountA.getBalance());
        assertEquals(5010, testBusinessAccount.getBalance());
    }

    @Test
    void testMakeRequest() {
        Transaction transaction = testAccountA.requestMoney(testAccountB, 200);
        assertTrue(testAccountA.getPendingTransactions().contains(transaction));
        assertEquals(1, testAccountB.getPendingTransactions().size());

        testAccountA.removeFromTransactions(transaction);
        assertFalse(testAccountA.getPendingTransactions().contains(transaction));
        testAccountA.addToTransactions(transaction);
        assertTrue(testAccountA.getPendingTransactions().contains(transaction));
    }

    @Test
    void testMakeMultipleRequests() {
        //sets up additional users/accounts to request money from
        User testUserC = new PersonalUser
                ("$moneymaker", "Toronto", "Bob", "Marley");
        Account testAccountC = new Account(testUserC, 500);

        //test account makes money request to test account 2
        testAccountA.requestMoney(testAccountB, 200);
        testAccountA.requestMoney(testAccountC, 500);
        testAccountA.requestMoney(testAccountB, 700);
        assertEquals(3, testAccountA.getPendingTransactions().size());
        assertEquals(2, testAccountB.getPendingTransactions().size());
        assertEquals(1, testAccountC.getPendingTransactions().size());

    }

    @Test
    void testAddCreditCard() {
        testAccountA.addCreditCard(testCard);
        assertEquals(1, testAccountA.getCreditCards().size());
    }

    @Test
    void testAddMultipleCreditCards() {
        CreditCard testCard2 = new CreditCard("Mastercard", 45679, 2300, 11);
        testAccountA.addCreditCard(testCard);
        testAccountA.addCreditCard(testCard2);
        assertEquals(2, testAccountA.getCreditCards().size());
    }

    @Test
    void testRemoveCreditCard() {
        testAccountA.addCreditCard(testCard);
        testAccountA.deleteCreditCard(testCard);
        assertEquals(0, testAccountA.getCreditCards().size());
    }

    @Test
    void testRemoveMultipleCreditCards() {
        CreditCard testCard2 = new CreditCard("Mastercard", 45679, 2300, 11);
        CreditCard testCard3 = new CreditCard("American Express", 1245679, 2100, 10);
        testAccountA.addCreditCard(testCard);
        testAccountA.addCreditCard(testCard2);
        testAccountA.addCreditCard(testCard3);
        testAccountA.deleteCreditCard(testCard3);
        testAccountA.deleteCreditCard(testCard);

        assertEquals(1, testAccountA.getCreditCards().size());
        assertTrue(testAccountA.getCreditCards().contains(testCard2));
    }

    //    //REQUIRE: valid boost
//    //MODIFY: this
//    //EFFECTS: add boost to this credit card, returns list of updated boosts
//    public List addBoost(Boost boost) {
//        return boosts;
//    }
//
//    //REQUIRE: valid boost that is already on the card
//    //MODIFY: this
//    //EFFECTS: remove boost from this credit card, returns list of updated boosts
//    public List removeBoost(Boost boost) {
//        return boosts;
//    }
}

