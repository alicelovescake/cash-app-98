package model;

import java.time.LocalDate; // import the LocalDate class
import java.time.Month; // import the timeMonth class
import java.util.List;
import java.util.UUID;

public class CreditCard {
    private String cardType;
    private int cardNumber;
    private int expiryYear;
    private int expiryMonth;
    private String id;
    private Boolean isValid;
    private LocalDate currentDate = LocalDate.now();
    private int currentMonth = currentDate.getMonthValue();
    private int currentYear = currentDate.getYear();

    //REQUIRES: cardType is valid and has non-zero length, cardNumber is valid
    //EFFECTS: creates a credit card with given parameters
    public CreditCard(String cardType, int cardNumber,
                      int expiryYear, int expiryMonth) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.id = UUID.randomUUID().toString();
        validateCard();
    }

    //Getters
    public String getCardType() {
        return cardType;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getId() {
        return id;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    //MODIFY: this
    //EFFECTS: sets isValid field to true if
    // card expiry year and month < current month and year
    public void validateCard() {
        isValid = expiryYear >= currentYear && expiryMonth > currentMonth;
    }

}
