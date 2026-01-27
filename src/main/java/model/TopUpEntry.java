package model;

import java.time.Instant;


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
