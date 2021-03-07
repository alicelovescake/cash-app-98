package model;

import org.json.JSONObject;

// A class to represent a business user like Amazon, with available methods and type
public class BusinessUser extends User {
    private String companyName;
//    private static final int dailyLimit = 10000;

    public enum BusinessType {
        CAFE, GROCERY, RETAILER, RESTAURANT, OTHER
    }

    private BusinessType businessType;

    //REQUIRES: company name has a non-zero length
    //EFFECTS: creates a business user, company name set.
    public BusinessUser(String username, String location, String companyName, BusinessType type) {
        super(username, location, UserType.BUSINESS);
        this.companyName = companyName;
        this.businessType = type;
    }

    //getters
    public BusinessType getBusinessType() {
        return businessType;
    }

    public Boolean referFriend(String email) {
        return false;
    }

    public Boolean referralReward() {
        return false;
    }

    @Override
    //EFFECTS: returns this boost as a JSON object
    public JSONObject toJson() {
        JSONObject userJson = new JSONObject();
        userJson.put("username", username);
        userJson.put("location", location);
        userJson.put("id", id);
        userJson.put("userType", userType);
        userJson.put("businessType", businessType);
        userJson.put("companyName", companyName);
        return userJson;
    }
}
