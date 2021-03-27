package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//class representing personal user which can refer friends, none business
public class PersonalUser extends User {
    private String firstName;
    private String lastName;


    //REQUIRES: first and last name has a non-zero length
    //EFFECTS: creates a personal user, first and last name set.
    public PersonalUser(String username, String location, String firstName, String lastName) {
        super(username, location, UserType.PERSONAL);
        this.firstName = firstName;
        this.lastName = lastName;
        referredFriends = new ArrayList<String>();
    }


    //REQUIRES: valid email address
    //MODIFY: this
    //EFFECTS: User adds email of friend to make a referral to cash app,
    //         When they make 5 referrals, they get rewarded with $5 cashback, referral count resets
    //         Returns number of referrals

    public Boolean referFriend(String email) {
        if (email.length() > 3 && !referredFriends.contains(email)) {
            referralCount++;
            referredFriends.add(email);
            referralReward();
            return true;
        } else {
            System.out.println("This email is not valid! Try again!");
            return false;
        }

    }

    //MODIFY: this
    //EFFECTS: if referral count is >= number needed for reward, account balance is incremented with cashback and
    // returns true, false otherwise
    public Boolean referralReward() {
        if (referralCount >= referralCountForReward) {
            this.account.incrementBalance(cashBackForReferral);
            referralCount -= referralCountForReward;
            return true;
        }
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
        userJson.put("firstName", firstName);
        userJson.put("lastName", lastName);
        userJson.put("referredFriends", referredFriendsToJson());

        return userJson;
    }

    //EFFECTS: returns referred friends from this user as a JSON array
    private JSONArray referredFriendsToJson() {
        JSONArray friendsJsonArray = new JSONArray();

        for (String friend : referredFriends) {
            friendsJsonArray.put(friend);
        }
        return friendsJsonArray;
    }

}
