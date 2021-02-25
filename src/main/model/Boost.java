package model;

import java.util.List;

public interface Boost {
    static double cashBack = 0;
    boolean applyBoost(Transaction transaction);
}
