package model.boosts;

import model.BusinessUser;
import model.Transaction;
import model.User;
import org.json.JSONObject;

// A boost to give cashback to purchases made to retailer
public class ShopaholicBoost implements Boost {
    BoostType boostType;

    //EFFECTS: creates an boost if recipient is a type retailer

    public ShopaholicBoost() {
        boostType = BoostType.SHOPAHOLIC;
    }

    @Override
    // REQUIRE: valid transaction
    // MODIFY: this
    // EFFECTS: If transaction recipient user type is Retailer, user gets 5 times cashback percentage (5%)
    // for total purchase
    public boolean applyBoost(Transaction transaction) {
        User user = transaction.getRecipient();

        if (user instanceof BusinessUser) {
            if (((BusinessUser) user).getBusinessType() == BusinessUser.BusinessType.RETAILER) {
                transaction.getSenderAccount().incrementBalance(cashBack * 5 * transaction.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public BoostType getBoostType() {
        return boostType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShopaholicBoost)) {
            return false;
        }
        ShopaholicBoost that = (ShopaholicBoost) o;
        return boostType == that.boostType;
    }

    @Override
    //EFFECTS: returns this boost as a JSON object
    public JSONObject toJson() {
        JSONObject boostJson = new JSONObject();
        boostJson.put("boostType", boostType);

        return boostJson;
    }
}
