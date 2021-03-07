package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionTest {
    private Transaction testTransaction;
    private Account testSender;
    private Account testReceiver;
    private User testUserSender;
    private User testUserReceiver;
    private CreditCard testCard;

    @BeforeEach
    void setup(){
        testUserSender = new PersonalUser
                ("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testUserReceiver = new PersonalUser
                ("$boblovespizza", "Vancouver", "Bob", "Marley");
        testSender = new Account(testUserSender, 100.0);
        testReceiver = new Account(testUserReceiver, 500.0);
        testTransaction = new Transaction(testReceiver, testSender, 100.0, Transaction.Type.EXCHANGE,
                Transaction.Status.PENDING);
    }

    @Test
    void testCompleteSingleExchangeTransaction(){
        assertEquals(Transaction.Status.COMPLETE, testTransaction.getStatus());
        assertEquals(0, testSender.getBalance());
        assertEquals(600, testReceiver.getBalance());
        assertEquals("$alicelovescake", testTransaction.getSenderUsername());
        assertEquals("$boblovespizza", testTransaction.getRecipientUsername());
        assertEquals(Transaction.Type.EXCHANGE, testTransaction.getType());
        assertTrue(testTransaction.getId() != null);
    }

    @Test
    void testCompleteMultipleExchangeTransaction(){
        //confirms first transaction went through
        assertEquals(Transaction.Status.COMPLETE, testTransaction.getStatus());
        //confirms second transaction went through (sender/receiver switched)
        Transaction testTransaction2 = new Transaction
                (testSender, testReceiver, 50.0, Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
        assertEquals(Transaction.Status.COMPLETE, testTransaction2.getStatus());
        //confirms final balance
        assertEquals(50, testSender.getBalance());
        assertEquals(550, testReceiver.getBalance());
    }

    @Test
    void testIncompleteFailedExchangeTransaction(){
        //confirms first transaction went through
        assertEquals(Transaction.Status.COMPLETE, testTransaction.getStatus());

        //confirms second transaction failed because of insufficient funds
        Transaction testTransaction2 = new Transaction
                (testReceiver, testSender, 1000.0, Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
        assertEquals(Transaction.Status.FAILED, testTransaction2.getStatus());

        //confirms final balance
        assertEquals(0, testSender.getBalance());
        assertEquals(600, testReceiver.getBalance());


    }

    @Test
    void testIncompleteTransactionStatus(){
        //confirms first transaction went through
        assertEquals(Transaction.Status.COMPLETE, testTransaction.getStatus());

        //confirms second transaction failed because of complete status
        Transaction testTransaction2 = new Transaction
                (testReceiver, testSender, 1000.0, Transaction.Type.EXCHANGE, Transaction.Status.COMPLETE);
        assertEquals(Transaction.Status.COMPLETE, testTransaction2.getStatus());

        //confirms final balance
        assertEquals(0, testSender.getBalance());
        assertEquals(600, testReceiver.getBalance());
    }

    @Test
    void testRequestTransaction(){
        //single request
        Transaction testRequestTransaction = new Transaction
                (testReceiver, testSender, 100.0, Transaction.Type.REQUEST, Transaction.Status.PENDING);
        assertEquals(Transaction.Status.PENDING, testRequestTransaction.getStatus());
        // multiple requests
        Transaction testRequestTransaction2 = new Transaction
                (testReceiver, testSender, 100.0, Transaction.Type.REQUEST, Transaction.Status.PENDING);
        assertEquals(Transaction.Status.PENDING, testRequestTransaction2.getStatus());
        Transaction testRequestTransaction3 = new Transaction
                (testReceiver, testSender, 100.0, Transaction.Type.REQUEST, Transaction.Status.PENDING);
        assertEquals(Transaction.Status.PENDING, testRequestTransaction3.getStatus());
        assertEquals(Transaction.Type.REQUEST, testRequestTransaction3.getType());


        assertEquals(0, testSender.getBalance());
        assertEquals(600, testReceiver.getBalance());
    }
}
