package persistence;

import model.Account;
import model.BusinessUser;
import model.PersonalUser;
import model.User;
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
        int balance = jsonObject.getInt("balance");
        JSONObject jsonUser = jsonObject.getJSONObject("user");
        String userType = jsonUser.getString("userType");
        String userName = jsonUser.getString("username");
        String location = jsonUser.getString("location");
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

        Account account = new Account(user, balance);
        addTransactions(account, jsonObject);
        return account;

    }

    // MODIFIES: account
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransactions(Account account, JSONObject jsonObject) {
        JSONArray transactionArray = jsonObject.getJSONArray("transactions");
        for (Object t : transactionArray) {
            JSONObject nextTransaction = (JSONObject) t;
            addTransaction(account, nextTransaction);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransaction(Account account, JSONObject transaction) {
        int cardNumber = transaction.getInt("cardNumber");
        int expiryMonth = transaction.getInt("expiryMonth");
        int expiryYear = transaction.getInt("expiryYear");

    }


    // MODIFIES: account
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addCreditCard(Account account, JSONObject creditCard) {
        String cardType = creditCard.getString("cardType");
        int cardNumber = creditCard.getInt("cardNumber");
        int expiryMonth = creditCard.getInt("expiryMonth");
        int expiryYear = creditCard.getInt("expiryYear");

    }

    // MODIFIES: account
    // EFFECTS: parses change from JSON object and adds them to account
    private void addChange(Account acc, JSONObject jsonObject) {
    }
}
