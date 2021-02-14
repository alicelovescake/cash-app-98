package model;

import java.util.List;
import java.util.UUID;

public abstract class User {
    protected String username;
    protected String location;
    protected String id;
    protected Account account;

    //REQUIRES: name has a non-zero length
    //EFFECTS: user is created with given name and location.
    //         User id and username is unique.
    public User(String user, String location) {
        this.username = user;
        this.location = location;
        this.id = UUID.randomUUID().toString();
        this.account = new Account(this, 0);
    }

    // getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public Account getAccount() {
        return account;
    }

}
