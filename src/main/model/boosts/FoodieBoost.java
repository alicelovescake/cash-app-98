package model.boosts;

import model.BusinessUser;
import model.Transaction;
import model.User;

public class FoodieBoost implements Boost {
    BoostType boostType;

    //EFFECTS: creates an boost if recipient is a type restaurant or cafe

    public FoodieBoost() {
        boostType = BoostType.FOODIE;
    }

    // user gets 3 times cashback percentage (3%) for total purchase

    @Override
    public boolean applyBoost(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RESTAURANT
                    || ((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.CAFE) {
                transaction.getSenderAccount().incrementBalance(3 * cashBack * transaction.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public BoostType getBoostType() {
        return boostType;
    }
}
