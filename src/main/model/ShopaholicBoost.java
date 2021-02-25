package model;

import static model.Boost.cashBack;

public class ShopaholicBoost implements Boost {
    //EFFECTS: creates an boost if recipient is a type retailer
    public ShopaholicBoost() {
    }

    @Override
    public boolean applyBoost(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RETAILER) {
                transaction.getSenderAccount().incrementBalance(cashBack * 5);
                return true;
            }
        }
        return false;
    }
}
