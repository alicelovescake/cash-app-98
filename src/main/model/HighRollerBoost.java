package model;

public class HighRollerBoost implements Boost {
    static double cashBack = 0.05; // 5 % cashback for high spend purchases

    //EFFECTS: creates an boost if amount is > 1000
    public HighRollerBoost() {

    }

    @Override
    public boolean applyBoost(Transaction transaction) {
        if (transaction.getAmount() >= 1000) {
            transaction.getSenderAccount().incrementBalance(cashBack * 100);
            return true;
        }
        return false;
    }
}


