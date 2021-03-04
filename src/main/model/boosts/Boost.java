package model.boosts;

import model.Transaction;

public interface Boost {
    double cashBack = 0.01;
    boolean applyBoost(Transaction transaction);
    BoostType getBoostType();
}
