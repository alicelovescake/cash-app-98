package model;

import model.boosts.Boost;
import model.boosts.FoodieBoost;
import model.boosts.HighRollerBoost;
import model.boosts.ShopaholicBoost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoostTest {

    private Account testAccountA;
    private User testUserA;

    private BusinessUser testBusinessUser;
    private CreditCard testCard;
    private  Boost highRoller;
    private  Boost shopaholic;
    private  Boost foodie;

    @BeforeEach
    void setUp() {
        testUserA = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testCard = new CreditCard("Visa", 123456, 2025, 12);
        testBusinessUser = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.RETAILER);
        testAccountA = new Account(testUserA, 100.50);
        highRoller = new HighRollerBoost();
        shopaholic = new ShopaholicBoost();
        foodie = new FoodieBoost();

    }

    @Test
    void testConstructor() {

    }
}
