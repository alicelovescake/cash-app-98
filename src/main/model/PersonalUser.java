package model;

public class PersonalUser extends User {
    private String firstName;
    private String lastName;
    private static final int dailyLimit = 3000;

    private int referralCount;
    private static final int referralCountForReward = 5;
    private static final int cashBackForReferral = 5;

    //REQUIRES: first and last name has a non-zero length
    //EFFECTS: creates a personal user, first and last name set.
    public PersonalUser(String username, String location, String firstName, String lastName) {
        super(username, location);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //getters

    public int getReferralCount() {
        return referralCount;
    }

    public int getReferralCountForReward() {
        return referralCountForReward;
    }
    //REQUIRES: valid email address
    //MODIFY: this
    //EFFECTS: User adds email of friend to make a referral to cash app,
    //         When they make 5 referrals, they get rewarded with $5 cashback, referral count resets
    //         Returns number of referrals

    public int referFriend(String email) {
        if (email.length() > 3) {
            referralCount++;
        }
        if (referralCount >= referralCountForReward) {
            this.account.incrementBalance(cashBackForReferral);
            referralCount -= referralCountForReward;
        }
        return referralCount;
    }

}
