package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Represents an account having an id, associated user, balance, list of credit cards and transactions
public class Account {
    private static int nextAccountId = 1;  // tracks id of next account created
    private static String id;                  // account id
    private User user;                   // the account owner name
    private double balance;               // the current balance of the account
    private List<CreditCard> creditCards; // list of credit cards added to this user account
    private List<Transaction> transactions; // list of completed transactions associated to this account

    //REQUIRES: valid user of Cash App and initial balance >= 0
    //EFFECTS: creates an account based on the user, balance on account is set
    //         to given initialBalance

    public Account(User user, double initialBalance) {
        this.balance = initialBalance;
        this.user = user;
        this.id = UUID.randomUUID().toString();
        this.creditCards = new ArrayList<>();
        this.transactions = new ArrayList<>();
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

//    public List getBoosts() {
//        return boosts;
//    }

    //Setter
    //increment and decrement separate setters to avoid mistakenly wiping out account
    public void incrementBalance(double amount) {
        this.balance += amount;
    }

    public void decrementBalance(double amount) {
        this.balance -= amount;
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


    // REQUIRES: amount >= 0 and valid credit card
    // MODIFY: this
    // EFFECTS: amount is added to balance from given card (if valid) and updated balance is returned
    public double deposit(CreditCard card, double amount) {
        if (card.getIsValid()) {
            this.balance += amount;
        }
        return balance;
    }

    //REQUIRES: amount >= 0 and valid credit card
    //MODIFY: this
    //EFFECTS: if sufficient funds, amount is withdrawn from account into given card, balance updated
    //          return true. If insufficient funds, return false and balance unchanged
    public Boolean withdraw(CreditCard card, double amount) {
        if (this.balance >= amount && card.getIsValid()) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    //REQUIRES: amount >= 0 and valid user of cash app as recipient
    //MODIFY: this
    //EFFECTS: new transaction created with this as sender and recipient as receiver.
    public Transaction sendMoney(Account recipient, double amount) {
        Transaction transaction = new Transaction(recipient, this, amount, Transaction.Type.EXCHANGE);
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
    public void makePurchase(Account company, double amount) {
        Transaction transaction = new Transaction(company, this, amount, Transaction.Type.EXCHANGE);
        transactions.add(transaction);
        company.addToTransactions(transaction);
    }

    //REQUIRES: amount >= 0 and valid user of cash app
    //MODIFY: this
    //EFFECTS: new transaction is created, updates list of pending transactions
    public Transaction requestMoney(Account user, double amount) {
        Transaction transaction = new Transaction(this, user, amount, Transaction.Type.REQUEST);
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
        }
    }

    //REQUIRES: valid credit card already in list, no duplicates
    //MODIFY: this
    //EFFECTS: given credit card is removed from account's list of credit cards
    public void deleteCreditCard(CreditCard card) {
        creditCards.remove(card);
    }


//    //REQUIRE: valid boost
//    //MODIFY: this
//    //EFFECTS: add boost to this credit card, returns list of updated boosts
//    public List addBoost(Boost boost) {
//        return boosts;
//    }
//
//    //REQUIRE: valid boost that is already on the card
//    //MODIFY: this
//    //EFFECTS: remove boost from this credit card, returns list of updated boosts
//    public List removeBoost(Boost boost) {
//        return boosts;
//    }

}
