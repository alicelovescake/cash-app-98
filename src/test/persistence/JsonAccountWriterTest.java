package persistence;

import model.*;
import model.boosts.Boost;
import model.boosts.ShopaholicBoost;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonAccountWriterTest {
    private Account testPersonalAccount;
    private User testUser;
    private CreditCard testCard;
    private User testBusinessUser;
    private Account testBusinessAccount;
    private Boost shopaholic;

    @Before
    void setup() {
        testUser = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testPersonalAccount = new Account(testUser, 100);
        testCard = new CreditCard("Visa", 123456, 2025, 12);
        testBusinessUser = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.RETAILER);
        testBusinessAccount = new Account(testBusinessUser, 5000);
        shopaholic = new ShopaholicBoost();
    }

    @Test
    void testAccountWriterInvalidFile() {
        try {
            JsonAccountWriter InvalidAccountWriter = new JsonAccountWriter("./data/:]illegal.json");
            InvalidAccountWriter.open();
            fail("Should have caught file not found exception");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testAccountWriterDefault() {
        try {
            JsonAccountWriter testAccountWriter =
                    new JsonAccountWriter("./data/testAccountWriterDefault.json");
            testAccountWriter.open();
            testAccountWriter.write(testPersonalAccount);
            testAccountWriter.close();

            JsonAccountReader reader = new JsonAccountReader("./data/testAccountWriterDefault.json");
            testPersonalAccount = reader.read();

            Assertions.assertEquals(100, testPersonalAccount.getBalance());
            assertEquals(testUser, testPersonalAccount.getUser());
            assertEquals(0, testPersonalAccount.getCreditCards().size());
            assertEquals(0, testPersonalAccount.getBoosts().size());
            assertEquals(0, testPersonalAccount.getCompletedTransactions().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAccountWriterWithAddedData() {
        try {
            testPersonalAccount.addCreditCard(testCard);
            //balance should be 1100 after deposit
            testPersonalAccount.deposit(testCard, 1000);
            //boosts should be size 1
            testPersonalAccount.addBoost(shopaholic);
            //balance should be 1100 - 100 + 5 cashback = 1005, completed transaction size 1
            testPersonalAccount.makePurchase(testBusinessAccount, 100);
            JsonAccountWriter testAccountWriter =
                    new JsonAccountWriter("./data/testAccountWriterWithData.json");
            testAccountWriter.open();
            testAccountWriter.write(testPersonalAccount);
            testAccountWriter.close();

            JsonAccountReader reader = new JsonAccountReader("./data/testAccountWriterWithData.json");
            testPersonalAccount = reader.read();
            assertEquals(testUser, testPersonalAccount.getUser());
            Assertions.assertEquals(1005, testPersonalAccount.getBalance());
            assertEquals(1, testPersonalAccount.getCreditCards().size());
            assertEquals(1, testPersonalAccount.getBoosts().size());
            assertEquals(1, testPersonalAccount.getCompletedTransactions().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
