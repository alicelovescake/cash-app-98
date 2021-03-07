package persistence;

import model.*;
import model.boosts.Boost;
import model.boosts.BoostType;
import model.boosts.FoodieBoost;
import model.boosts.ShopaholicBoost;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JsonAccountWriterTest extends JsonAccountTest{
    private Account testPersonalAccount;
    private User testUser;
    private CreditCard testCard;
    private User testBusinessUser;
    private Account testBusinessAccount;
    private Boost shopaholic;
    private Boost foodie;

    @Before
    void setup() {
        testUser = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testPersonalAccount = new Account(testUser, 100);
        testCard = new CreditCard("Visa", 123456, 2025, 12);
        testBusinessUser = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.RETAILER);
        testBusinessAccount = new Account(testBusinessUser, 5000);
        shopaholic = new ShopaholicBoost();
        foodie = new FoodieBoost();
    }

    @Test
    void testAccountWriterInvalidFile() {
        try {
            JsonAccountWriter InvalidAccountWriter = new JsonAccountWriter("./data/my\0illegal:fileName.json");
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
            Account readPersonalAccount = reader.read();

            //check fields with keys: Balance and account id
            Assertions.assertEquals(100, readPersonalAccount.getBalance());
            assertEquals(readPersonalAccount.getId(), testPersonalAccount.getId());

            assertEquals(0, readPersonalAccount.getCreditCards().size());
            assertEquals(0, readPersonalAccount.getBoosts().size());
            assertEquals(0, readPersonalAccount.getCompletedTransactions().size());

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
            testPersonalAccount.addBoost(foodie);
            //balance should be 1100 - 100 + 5 cashback = 1005, completed transaction size 1
            testPersonalAccount.makePurchase(testBusinessAccount, 100);
            JsonAccountWriter testAccountWriter =
                    new JsonAccountWriter("./data/testAccountWriterWithData.json");
            testAccountWriter.open();
            testAccountWriter.write(testPersonalAccount);
            testAccountWriter.close();

            JsonAccountReader reader = new JsonAccountReader("./data/testAccountWriterWithData.json");
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
            fail("Exception should not have been thrown");
        }
    }

}
