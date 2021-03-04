package model;

import model.boosts.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoostTest {

    private Account testAccountA;
    private Account testRetailAccount;
    private Account testRestaurantAccount;
    private Account testCafeAccount;
    private User testUserA;

    private BusinessUser testBusinessUserRetail;
    private BusinessUser testBusinessUserRestaurant;
    private BusinessUser testBusinessUserCafe;
    private  Boost highRoller;
    private  Boost shopaholic;
    private  Boost foodie;

    @BeforeEach
    void setUp() {
        testUserA = new PersonalUser("$alicelovescake", "Vancouver", "Alice", "Zhao");
        testBusinessUserRetail = new BusinessUser(
                "$amazon", "Seattle", "Amazon", BusinessUser.BusinessType.RETAILER);
        testBusinessUserRestaurant = new BusinessUser(
                "$indian", "Seattle", "Indian Food", BusinessUser.BusinessType.RESTAURANT);
        testBusinessUserCafe = new BusinessUser(
                "$starbucks", "Seattle", "Starbucks", BusinessUser.BusinessType.CAFE);
        testAccountA = new Account(testUserA, 2000);
        testRetailAccount = new Account(testBusinessUserRetail, 100);
        testRestaurantAccount = new Account(testBusinessUserRestaurant, 100);
        testCafeAccount = new Account(testBusinessUserCafe, 100);
        highRoller = new HighRollerBoost();
        shopaholic = new ShopaholicBoost();
        foodie = new FoodieBoost();

    }

    @Test
    void testConstructor() {
        assertEquals(BoostType.HIGHROLLER, highRoller.getBoostType());
        assertEquals(BoostType.SHOPAHOLIC, shopaholic.getBoostType());
        assertEquals(BoostType.FOODIE, foodie.getBoostType());
    }

    @Test
    void testApplyBoostHighRollerCashBack(){
        testAccountA.addBoost(highRoller);
        Transaction transaction =
                new Transaction(testRetailAccount, testAccountA, 1000, Transaction.Type.EXCHANGE);
        assertTrue(highRoller.applyBoost(transaction));
        assertEquals(1100, testAccountA.getBalance());
    }

    @Test
    void testApplyBoostShopaholicCashBack(){
        testAccountA.addBoost(shopaholic);
        Transaction transaction =
                new Transaction(testRetailAccount, testAccountA, 100, Transaction.Type.EXCHANGE);
        assertTrue(shopaholic.applyBoost(transaction));
        assertEquals(1905, testAccountA.getBalance());
    }

    @Test
    void testApplyBoostFoodieCashBack(){
        testAccountA.addBoost(foodie);
        Transaction transaction =
                new Transaction(testCafeAccount, testAccountA, 10, Transaction.Type.EXCHANGE);
        assertTrue(foodie.applyBoost(transaction));
        assertEquals(1990.30, testAccountA.getBalance());

        Transaction transaction2 =
                new Transaction(testRestaurantAccount, testAccountA, 100, Transaction.Type.EXCHANGE);
        assertTrue(foodie.applyBoost(transaction2));
        assertEquals(1893.3, testAccountA.getBalance());
    }

    @Test
    void testApplyBoostNoCashBack(){
        Transaction transaction =
                new Transaction(testRetailAccount, testAccountA, 999, Transaction.Type.EXCHANGE);
        assertFalse(highRoller.applyBoost(transaction));
        assertEquals(1001, testAccountA.getBalance());
    }

    @Test
    void testApplyBoostFalse(){
        Transaction transaction =
                new Transaction(testRestaurantAccount, testAccountA, 100, Transaction.Type.EXCHANGE);
        assertFalse(shopaholic.applyBoost(transaction));

        Transaction transaction2 =
                new Transaction(testRetailAccount, testAccountA, 100, Transaction.Type.EXCHANGE);
        assertFalse(foodie.applyBoost(transaction2));
    }
}
