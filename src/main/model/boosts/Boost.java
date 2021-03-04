package model.boosts;

import model.Transaction;

//represents what a boost consists of, and methods to apply a boost
public interface Boost {
    double cashBack = 0.01;

    boolean applyBoost(Transaction transaction);

    BoostType getBoostType();
}
