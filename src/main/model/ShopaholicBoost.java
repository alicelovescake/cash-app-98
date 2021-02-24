package model;

import static model.Boost.cashBack;

public class ShopaholicBoost extends Boost {
    //EFFECTS: creates an boost if recipient is a type retailer
    public ShopaholicBoost(Transaction transaction) {
        super(transaction);
    }

    @Override
    public boolean validateTransaction(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RETAILER) {
                transaction.getSenderAccount().incrementBalance(cashBack * 2);
                return true;
            }
        }
        return false;
    }
}
