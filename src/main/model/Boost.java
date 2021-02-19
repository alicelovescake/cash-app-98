package model;

import java.util.List;

public abstract class Boost {
    protected List<Boost> availableBoosts;
    protected int cashBack;
    protected Transaction transaction;

    //EFFECTS: Boost is created with current transaction, not yet validated
    public Boost(Transaction transaction) {
        this.transaction = transaction;
    }

    // getters
    public List getAvailableBoosts() {
        return availableBoosts;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    protected abstract void validateTransaction(Transaction transaction);

}
