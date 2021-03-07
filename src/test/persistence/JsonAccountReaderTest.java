package persistence;

import model.*;
import model.boosts.Boost;
import model.boosts.FoodieBoost;
import model.boosts.ShopaholicBoost;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonAccountReaderTest extends JsonAccountTest {
    private Account testPersonalAccount;
    private User testUser;
    private User testBusinessUser;
    private Account testBusinessAccount;
    private Boost shopaholic;
    private Boost foodie;

    @BeforeEach
    void setup() {
        testUser = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testPersonalAccount = new Account(testUser, 100);
        testBusinessUser = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.RETAILER);
        testBusinessAccount = new Account(testBusinessUser, 5000);
        shopaholic = new ShopaholicBoost();
        foodie = new FoodieBoost();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonAccountReader invalidReader = new JsonAccountReader("./data/fileNotFound.json");
        try {
            Account testAccount = invalidReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // all pass
        }
    }

    @Test
    void testReaderAccountDefault() {
        JsonAccountReader reader = new JsonAccountReader("./data/testAccountWriterDefault.json");
        try {
            Account readAccount = reader.read();

            Assertions.assertEquals(100, readAccount.getBalance());
            assertEquals(0, readAccount.getCreditCards().size());
            assertEquals(0, readAccount.getBoosts().size());
            assertEquals(0, readAccount.getCompletedTransactions().size());

        } catch(IOException e) {
            fail("Oops! This file cannot be read");
        }

    }

    @Test
    void testReadAccountWithAddedData() {
        JsonAccountReader reader = new JsonAccountReader("./data/testAccountWriterWithData.json");
        try {
            Account readPersonalAccount = reader.read();

            //check fields with keys: Balance and account id
            Assertions.assertEquals(1005, readPersonalAccount.getBalance());
            assertEquals(readPersonalAccount.getId(), testPersonalAccount.getId());

            //gets all lists of objects from JSON and checks size
            List<CreditCard> cards = readPersonalAccount.getCreditCards();
            assertEquals(1, cards.size());
            List<Transaction> transactions = readPersonalAccount.getCompletedTransactions();
            assertEquals(1, transactions.size());
            Set<Boost> boosts = readPersonalAccount.getBoosts();
            assertEquals(2, boosts.size());

            //check objects in JSON file
            checkUser("$alicelovescake", "Vancouver", testUser.getId(), User.UserType.PERSONAL,
                    readPersonalAccount.getUser());
            checkCreditCard("Visa", 123456, 2025, 12,
                    (CreditCard) readPersonalAccount.getCreditCards().get(0));
            Transaction testTransaction = (Transaction) testPersonalAccount.getCompletedTransactions().get(0);
            checkTransaction(testBusinessAccount, testPersonalAccount, testTransaction.getId(), testTransaction.getDate(),
                    100, Transaction.Status.COMPLETE, Transaction.Type.EXCHANGE, transactions.get(0));
            checkBoost(shopaholic, boosts);
            checkBoost(foodie, boosts);
        } catch (IOException e) {
            fail("Oops! This file cannot be read");
        }

    }
}
