package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate; // import the LocalDate class
import java.util.UUID;

//Represents a credit card with type, number, expiry month and year
public class CreditCard implements Writable {
    private String cardType;
    private long cardNumber;
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

    public long getCardNumber() {
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

    @Override
    //EFFECTS: returns credit cards in this account as a JSON object
    public JSONObject toJson() {
        JSONObject cardJson = new JSONObject();
        cardJson.put("cardType", cardType);
        cardJson.put("cardNumber", cardNumber);
        cardJson.put("id", id);
        cardJson.put("expiryMonth", expiryMonth);
        cardJson.put("expiryYear", expiryYear);
        return cardJson;
    }

}
