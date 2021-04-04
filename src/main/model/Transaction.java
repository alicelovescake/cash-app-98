package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.UUID;

// class representing a transaction every time a purchase or request for money is made
public class Transaction implements Writable {
    private Account recipient;
    private Account sender;
    private String id;
    private double amount;
    private LocalDate date;

    //Pending: money has yet to be changed hands, complete: money has changed hands, failed, money hasn't changed hands
    public enum Status {
        PENDING, COMPLETE, FAILED
    }

    public enum Type {
        REQUEST, EXCHANGE
    }

    private Status status;
    private Type type;

    //EFFECTS: constructor sets fields and checks if type is not request and it is pending, then complete transaction
    public Transaction(Account recipient, Account sender, double amount, Type type, Status status) {
        this.recipient = recipient;
        this.amount = amount;
        this.sender = sender;
        this.id = UUID.randomUUID().toString();
        this.date = LocalDate.now();
        this.status = status;
        this.type = type;

        if (this.type != Type.REQUEST && this.status == Status.PENDING) {
            completeTransaction();
        }

    }

    // getters
    public Status getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getSenderUsername() {
        return sender.getUser().getUsername();
    }

    public String getRecipientUsername() {
        return recipient.getUser().getUsername();
    }

    public User getRecipient() {
        return recipient.getUser();
    }

    public Account getRecipientAccount() {
        return recipient;
    }

    public Account getSenderAccount() {
        return sender;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    //setter
    public void setId(String id) {
        this.id = id;
    }

    //MODIFY: this
    // EFFECTS: Process transaction, status changes to complete if sender has sufficient funds, status FAILED otherwise
    public void completeTransaction() {
        if (sender.getBalance() >= amount) {
            try {
                sender.decrementBalance(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            recipient.incrementBalance(amount);
            this.status = Status.COMPLETE;
        } else {
            this.status = Status.FAILED;
        }
    }

    @Override
    //EFFECTS: returns transactions in this account as a JSON object
    public JSONObject toJson() {
        JSONObject transactionJson = new JSONObject();
        transactionJson.put("recipient", accountToJson(recipient));
        transactionJson.put("sender", accountToJson(sender));
        transactionJson.put("id", id);
        transactionJson.put("date", date);
        transactionJson.put("amount", amount);
        transactionJson.put("status", status);
        transactionJson.put("type", type);
        return transactionJson;
    }

    //EFFECTS: returns account as JSON object
    public JSONObject accountToJson(Account acc) {
        JSONObject accountJson = new JSONObject();
        accountJson.put("user", acc.getUser().toJson());
        accountJson.put("balance", acc.getBalance());
        accountJson.put("id", acc.getId());

        return accountJson;
    }
}
