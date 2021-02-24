package model;

import java.util.List;

public abstract class Boost {
    protected List<Boost> availableBoosts;
    protected static final int cashBack = 1; // cashback amount given to validatedTransactions
    protected Transaction transaction;

    //EFFECTS: Boost is created with current transaction, not yet validated
    public Boost(Transaction transaction) {
        if (transaction.getType() == Transaction.Type.EXCHANGE) {
            this.transaction = transaction;
        }
    }

    // getters
    public List getAvailableBoosts() {
        return availableBoosts;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    protected abstract boolean validateTransaction(Transaction transaction);

}
