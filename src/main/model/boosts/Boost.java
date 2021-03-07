package model.boosts;

import model.Transaction;
import persistence.Writable;

//represents what a boost consists of, and methods to apply a boost
public interface Boost extends Writable {
    double cashBack = 0.01;

    boolean applyBoost(Transaction transaction);

    BoostType getBoostType();
}
