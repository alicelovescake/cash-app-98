package model;

// A class to represent a business user like Amazon, with available methods and type
public class BusinessUser extends User {
    private String companyName;
//    private static final int dailyLimit = 10000;

    public enum BusinessType {
        CAFE, GROCERY, RETAILER, RESTAURANT, OTHER
    }

    private BusinessType type;

    //REQUIRES: company name has a non-zero length
    //EFFECTS: creates a business user, company name set.
    public BusinessUser(String username, String location, String companyName, BusinessType type) {
        super(username, location, UserType.BUSINESS);
        this.companyName = companyName;
        this.type = type;
    }

    //getters
    public BusinessType getBusinessType() {
        return type;
    }

    public Boolean referFriend(String email) {
        return false;
    }

    public Boolean referralReward() {
        return false;
    }
}
