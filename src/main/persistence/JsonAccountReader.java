package persistence;

import model.*;
import model.boosts.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//CITATION: Structure of this interface is modeled after JsonSerializationDemo
//          URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/
// Represents a reader that reads account info from JSON data stored in file
public class JsonAccountReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonAccountReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        Account account = jsonAccountToAccount(jsonObject);
        addTransactions(account, jsonObject);
        addCreditCards(account, jsonObject);
        addBoosts(account, jsonObject);
        return account;

    }

    // EFFECTS: parses transactions from JSON object and runs add transaction
    private void addTransactions(Account account, JSONObject jsonObject) {
        JSONArray transactionArray = jsonObject.getJSONArray("transactions");
        for (Object t : transactionArray) {
            JSONObject nextTransaction = (JSONObject) t;
            addTransaction(account, nextTransaction);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses transaction from JSON object and adds them to account
    private void addTransaction(Account account, JSONObject transactionJson) {
        int amount = transactionJson.getInt("amount");
        String type = transactionJson.getString("type");
        String status = transactionJson.getString("status");
        String id = transactionJson.getString("id");

        JSONObject recipient = transactionJson.getJSONObject("recipient");
        Account modelRecipientAcc = jsonAccountToAccount(recipient);

        JSONObject sender = transactionJson.getJSONObject("sender");
        Account modelSenderAcc = jsonAccountToAccount(sender);

        Transaction transaction = new Transaction(modelRecipientAcc, modelSenderAcc,
                amount, Transaction.Type.valueOf(type), Transaction.Status.valueOf(status));

        transaction.setId(id);

        account.addToTransactions(transaction);
    }

    // EFFECTS: parses credit cards from JSON object and runs add card method
    private void addCreditCards(Account account, JSONObject jsonObject) {
        JSONArray creditCardsArray = jsonObject.getJSONArray("creditCards");
        for (Object c : creditCardsArray) {
            JSONObject nextCard = (JSONObject) c;
            addCreditCard(account, nextCard);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses credit card from JSON object and adds them to account
    private void addCreditCard(Account account, JSONObject creditCard) {
        String cardType = creditCard.getString("cardType");
        int cardNumber = creditCard.getInt("cardNumber");
        int expiryMonth = creditCard.getInt("expiryMonth");
        int expiryYear = creditCard.getInt("expiryYear");
        CreditCard card = new CreditCard(cardType, cardNumber, expiryYear, expiryMonth);

        account.addCreditCard(card);
    }

    // EFFECTS: parses boosts from JSON object and runs add boost method

    private void addBoosts(Account account, JSONObject jsonObject) {
        JSONArray boostsArray = jsonObject.getJSONArray("boosts");
        for (Object b : boostsArray) {
            JSONObject nextBoost = (JSONObject) b;
            addBoost(account, nextBoost);
        }
    }

    //MODIFIES: account
    //EFFECTS: parses boost info from JSON objects and creates boost to add to account
    private void addBoost(Account acc, JSONObject boostJson) {
        String boostType = boostJson.getString("boostType");
        BoostType enumType = BoostType.valueOf(boostType);
        Boost boost;

        switch (enumType) {
            case SHOPAHOLIC:
                boost = new ShopaholicBoost();
                break;
            case FOODIE: 
                boost = new FoodieBoost();
                break;
            case HIGHROLLER:
                boost = new HighRollerBoost();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + enumType);
        }

        acc.addBoost(boost);

    }

    //EFFECTS: creates an account from JSON account object
    private Account jsonAccountToAccount(JSONObject jsonAccount) {
        int balance = jsonAccount.getInt("balance");
        String id = jsonAccount.getString("id");
        JSONObject jsonUser = jsonAccount.getJSONObject("user");
        User modelUser = jsonUserToUser(jsonUser);
        Account modelAcc = new Account(modelUser, balance);
        modelAcc.setId(id);

        return modelAcc;
    }


    //EFFECTS: creates a user based on json user
    private User jsonUserToUser(JSONObject jsonUser) {
        String userType = jsonUser.getString("userType");
        String userName = jsonUser.getString("username");
        String location = jsonUser.getString("location");
        String id = jsonUser.getString("id");
        User user;

        if (User.UserType.valueOf(userType) == User.UserType.PERSONAL) {
            String firstName = jsonUser.getString("firstName");
            String lastName = jsonUser.getString("lastName");
            user = new PersonalUser(userName, location, firstName, lastName);
        } else {
            String companyName = jsonUser.getString("companyName");
            String businessType = jsonUser.getString("businessType");
            user = new BusinessUser(userName, location, companyName, BusinessUser.BusinessType.valueOf(businessType));
        }
        user.setId(id);

        return user;
    }
}
