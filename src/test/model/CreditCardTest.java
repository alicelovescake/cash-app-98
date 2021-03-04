package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int testMonth = currentMonth == 1 ? 12 : currentMonth - 1;

        assertTrue(testCard.getIsValid());

        CreditCard testExpiredYearCard = new CreditCard
                ("Visa", 17897, 1999, 11);
        assertFalse(testExpiredYearCard.getIsValid());

        CreditCard testExpiredMonthCard = new CreditCard
                ("Visa", 17897, 2020, testMonth);
        assertFalse(testExpiredMonthCard.getIsValid());

        CreditCard testExpiredCard = new CreditCard
                ("Visa", 17897, 1999, testMonth);
        assertFalse(testExpiredCard.getIsValid());
    }
}

