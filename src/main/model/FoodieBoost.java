package model;

public class FoodieBoost extends Boost {
    //EFFECTS: creates an boost if recipient is a type restaurant or grocery
    public FoodieBoost(Transaction transaction) {
        super(transaction);
    }

    @Override
    public void validateTransaction(Transaction transaction) {

    }
}

