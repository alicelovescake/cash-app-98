package model.boosts;

import model.Transaction;
import org.json.JSONObject;
// A boost to give cashback for purchases > 1000

public class HighRollerBoost implements Boost {
    private BoostType boostType;
    //EFFECTS: creates an boost if amount is > 1000

    public HighRollerBoost() {
        boostType = BoostType.HIGHROLLER;
    }

    @Override
    // user gets 5 times cashback percentage (5%) for total purchase
    public boolean applyBoost(Transaction transaction) {
        if (transaction.getAmount() >= 1000) {
            transaction.getSenderAccount().incrementBalance(cashBack * 10 * transaction.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public BoostType getBoostType() {
        return boostType;
    }

    @Override
    //EFFECTS: returns this boost as a JSON object
    public JSONObject toJson() {
        JSONObject boostJson = new JSONObject();
        boostJson.put("boostType", boostType);

        return boostJson;
    }
}


