package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    private CreditCard testCard;

    @BeforeEach
    void setup(){
        testCard = new CreditCard("Visa", 12345, 2025, 12);
    }

    @Test
    void testConstructor(){
        assertEquals("Visa", testCard.getCardType());
        assertEquals(12345, testCard.getCardNumber());
        assertEquals(12, testCard.getExpiryMonth());
        assertEquals(2025, testCard.getExpiryYear());
        assertTrue(testCard.getId().length() > 0);
    }

    @Test
    void testValidateCard(){
        assertTrue(testCard.getIsValid());
        CreditCard testExpiredYearCard = new CreditCard
                ("Visa", 17897, 1999, 11);
        assertFalse(testExpiredYearCard.getIsValid());
        CreditCard testExpiredMonthCard = new CreditCard
                ("Visa", 17897, 2020, 1);
        assertFalse(testExpiredYearCard.getIsValid());
    }
}

