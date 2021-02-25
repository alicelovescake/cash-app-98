package model;

public class FoodieBoost implements Boost {
    static double cashBack = 0.02; // 2 % cashback for food purchases

    //EFFECTS: creates an boost if recipient is a type restaurant or cafe
    public FoodieBoost() {

    }

    @Override
    public boolean applyBoost(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RESTAURANT
                    || ((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.CAFE) {
                transaction.getSenderAccount().incrementBalance(3 * cashBack);
                return true;
            }
        }
        return false;
    }
}

