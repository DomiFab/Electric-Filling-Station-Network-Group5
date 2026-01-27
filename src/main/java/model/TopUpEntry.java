package model;

import java.time.Instant;

/**
 * Records a prepaid top-up operation on a customer account.
 */
public class TopUpEntry {
    private final Instant time;
    private final double amount;

    public TopUpEntry(Instant time, double amount) {
        this.time = time;
        this.amount = amount;
    }

    public Instant getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }
}
