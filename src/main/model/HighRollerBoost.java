package model;

public class HighRollerBoost extends Boost {
    //EFFECTS: creates an boost if amount is > 1000
    public HighRollerBoost(Transaction transaction) {
        super(transaction);
    }

    @Override
    public boolean validateTransaction(Transaction transaction) {
        if (transaction.getAmount() >= 1000) {
            transaction.getSenderAccount().incrementBalance(cashBack * 100);
            return true;
        }
        return false;
    }
}


