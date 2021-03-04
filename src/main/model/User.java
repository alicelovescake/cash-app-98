package model;

import java.util.List;
import java.util.UUID;

// abstract class for user with business and person user extending it.
public abstract class User {
    private String username;
    private String location;
    private String id;
    protected Account account;
    private UserType userType;
    protected int referralCount;
    protected static final int referralCountForReward = 5;
    protected static final int cashBackForReferral = 5;
    protected List<String> referredFriends;

    public enum UserType {
        PERSONAL, BUSINESS
    }

    //REQUIRES: name has a non-zero length
    //EFFECTS: user is created with given name and location.
    //         User id and username is unique.
    public User(String user, String location, UserType userType) {
        this.username = user;
        this.location = location;
        this.id = UUID.randomUUID().toString();
        this.account = new Account(this, 0);
        this.userType = userType;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getLocation() {
        return location;
    }

    public Account getAccount() {
        return account;
    }

    public int getReferralCount() {
        return referralCount;
    }

    public int getCashBackForReferral() {
        return cashBackForReferral;
    }


    public int getReferralCountForReward() {
        return referralCountForReward;
    }

    //REQUIRES: valid email address
    //MODIFY: this
    //EFFECTS: User adds email of friend to make a referral to cash app,
    //         When they make 5 referrals, they get rewarded with $5 cashback, referral count resets
    //         Returns number of referrals

    public abstract Boolean referFriend(String email);

    public abstract Boolean referralReward();

}
