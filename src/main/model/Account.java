package model;

import model.exceptions.InsufficientFundsException;
import model.boosts.*;
import model.exceptions.InvalidCardException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;
import java.util.stream.Collectors;

// Represents an account having an id, associated user, balance, list of credit cards and transactions
public class Account implements Writable {
    private static String id;                  // account id
    private User user;                   // the account owner name
    private double balance;               // the current balance of the account
    private List<CreditCard> creditCards; // list of credit cards added to this user account
    private List<Transaction> transactions; // list of completed transactions associated to this account
    private Set<Boost> boosts; // list of selected boosts associated to this account
    private static Boost highRoller = new HighRollerBoost();  // Boost available to account
    private static Boost shopaholic = new ShopaholicBoost(); // Boost available to account
    private static Boost foodie = new FoodieBoost();    // Boost available to account

    //REQUIRES: valid user of Cash App and initial balance >= 0
    //EFFECTS: creates an account based on the user, balance on account is set
    //         to given initialBalance
    public Account(User user, double initialBalance) {
        this.balance = initialBalance;
        this.user = user;
        this.id = UUID.randomUUID().toString();
        this.creditCards = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.boosts = new HashSet<>();
    }

    //Getters
    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public List getCreditCards() {
        return creditCards;
    }

    public List getCompletedTransactions() {
        List<Transaction> completedTransactions = transactions
                .stream()
                .filter(transaction -> transaction.getStatus() == Transaction.Status.COMPLETE)
                .collect(Collectors.toList());
        return completedTransactions;
    }

    public List getPendingTransactions() {
        List<Transaction> pendingTransactions = transactions
                .stream()
                .filter(transaction -> transaction.getStatus() == Transaction.Status.PENDING)
                .collect(Collectors.toList());
        return pendingTransactions;
    }

    public List getFailedTransactions() {
        List<Transaction> failedTransactions = transactions
                .stream()
                .filter(transaction -> transaction.getStatus() == Transaction.Status.FAILED)
                .collect(Collectors.toList());
        return failedTransactions;
    }

    public Set getBoosts() {
        return boosts;
    }

    public Boost getHighRollerBoost() {
        return highRoller;
    }

    public Boost getShopaholicBoost() {
        return shopaholic;
    }

    public Boost getFoodieBoost() {
        return foodie;
    }

    //Setter
    public void setId(String id) {
        this.id = id;
    }

    //increment and decrement separate setters to avoid mistakenly wiping out account
    public void incrementBalance(double amount) {
        this.balance += amount;
    }

    // Effects: Decrement balance, throws insufficient funds exception is amount is greater than balance
    public void decrementBalance(double amount) throws InsufficientFundsException {
        if (amount <= this.balance) {
            this.balance -= amount;
        } else {
            throw new InsufficientFundsException("Balance is going to be negative!");
        }
    }

    //MODIFY: this
    //EFFECT: updates list of completed transactions by adding completed
    public void addToTransactions(Transaction transaction) {
        transactions.add(transaction);
    }

    //REQUIRES: non empty pending Transaction, given transaction is contained in pending list
    //MODIFY: this
    //EFFECT: updates list of pending transactions by removing completed
    public void removeFromTransactions(Transaction transaction) {
        transactions.remove(transaction);
    }


    // MODIFY: this
    // EFFECTS: amount is added to balance from given card (if valid) and updated balance is returned, throws invalid
    // card exception if card is not valid
    public double deposit(CreditCard card, double amount) throws InvalidCardException {
        if (card.getIsValid()) {
            this.balance += amount;
        } else {
            throw new InvalidCardException("Credit Card Not Valid!");
        }
        return balance;
    }

    //MODIFY: this
    //EFFECTS: if sufficient funds, amount is withdrawn from account into given card, balance updated
    //          return true. If insufficient funds, throws insufficient funds exception return false and balance
    //          unchanged. If invalid card, throws invalid card exception

    public Boolean withdraw(CreditCard card, double amount) throws InvalidCardException, InsufficientFundsException {
        if (!card.getIsValid()) {
            throw new InvalidCardException("Credit Card Not Valid");
        }
        if (this.balance < amount) {
            throw new InsufficientFundsException("Not enough balance!");
        } else {
            this.balance -= amount;
            return true;
        }

    }

    //REQUIRES: amount >= 0 and valid user of cash app as recipient
    //MODIFY: this
    //EFFECTS: new transaction created with this as sender and recipient as receiver.
    public Transaction sendMoney(Account recipient, double amount) {
        Transaction transaction = new Transaction(recipient, this, amount, Transaction.Type.EXCHANGE,
                Transaction.Status.PENDING);
        transactions.add(transaction);
        recipient.addToTransactions(transaction);
        return transaction;
    }

    //REQUIRES: amount >= 0
    //MODIFY: this
    //EFFECTS: amount is deposited into account. Updated balance returned.
    public double receiveMoney(double amount) {
        this.balance += amount;
        return balance;
    }

    //REQUIRES: amount >= 0 and valid Business user of cash app as recipient
    //MODIFY: this
    //EFFECTS: new transaction created with this as sender and company as receiver, updates list of pending transactions
    public Transaction makePurchase(Account company, double amount) {
        Transaction transaction = new Transaction(company, this, amount, Transaction.Type.EXCHANGE,
                Transaction.Status.PENDING);
        transactions.add(transaction);
        company.addToTransactions(transaction);
        for (Boost b : boosts) {
            b.applyBoost(transaction);
        }
        return transaction;
    }

    //REQUIRES: amount >= 0 and valid user of cash app
    //MODIFY: this
    //EFFECTS: new transaction is created, updates list of pending transactions
    public Transaction requestMoney(Account user, double amount) {
        Transaction transaction = new Transaction(this,
                user,
                amount,
                Transaction.Type.REQUEST,
                Transaction.Status.PENDING);
        transactions.add(transaction);
        user.addToTransactions(transaction);
        return transaction;
    }

    //REQUIRES: no duplicates in list
    //MODIFY: this
    //EFFECTS: new credit card is added to account's list of credit cards if valid
    public void addCreditCard(CreditCard newCard) {
        if (newCard.getIsValid()) {
            creditCards.add(newCard);
        } else {
            System.out.println("Invalid credit card!");
        }
    }

    //REQUIRES: valid credit card already in list, no duplicates
    //MODIFY: this
    //EFFECTS: given credit card is removed from account's list of credit cards
    public void deleteCreditCard(CreditCard card) {
        creditCards.remove(card);
    }


    //REQUIRE: valid boost type
    //MODIFY: this
    //EFFECTS: add boost to this account (max 2), returns true if boost is added, false otherwise
    public Boolean addBoost(Boost boost) {
        if (boosts.size() >= 2) {
            return false;
        }
        boosts.add(boost);
        return true;
    }

    //MODIFY: this
    //EFFECTS: remove boost from this account, returns true if removed, false otherwise
    public Boolean removeBoost(Boost boost) {
        if (boosts.contains(boost)) {
            boosts.remove(boost);
            return true;
        } else {
            return false;
        }

    }

    @Override
    //EFFECTS: returns this account as a JSON object
    public JSONObject toJson() {
        JSONObject accountJson = new JSONObject();
        accountJson.put("balance", balance);
        accountJson.put("id", id);
        accountJson.put("user", user.toJson());
        accountJson.put("creditCards", cardsToJson());
        accountJson.put("transactions", transactionsToJson());
        accountJson.put("boosts", boostsToJson());
        return accountJson;
    }

    //EFFECTS: returns credit cards in this account as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonCreditCardArray = new JSONArray();

        for (CreditCard c : creditCards) {
            jsonCreditCardArray.put(c.toJson());
        }

        return jsonCreditCardArray;
    }

    //EFFECTS: returns transactions in this account as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonTransactionArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonTransactionArray.put(t.toJson());
        }

        return jsonTransactionArray;
    }

    //EFFECTS: returns boosts in this account as a JSON array
    private JSONArray boostsToJson() {
        JSONArray jsonBoostsArray = new JSONArray();

        for (Boost b : boosts) {
            jsonBoostsArray.put(b.toJson());
        }

        return jsonBoostsArray;
    }


}
