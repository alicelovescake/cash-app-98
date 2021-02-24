package model;

public class FoodieBoost extends Boost {
    //EFFECTS: creates an boost if recipient is a type restaurant or cafe
    public FoodieBoost(Transaction transaction) {
        super(transaction);
    }

    @Override
    public boolean validateTransaction(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RESTAURANT
                    || ((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.CAFE) {
                transaction.getSenderAccount().incrementBalance(cashBack);
                return true;
            }
        }
        return false;
    }
}

