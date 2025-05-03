package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;
@AllArgsConstructor
@Getter
public class SeatLock {
    Seat seat;
    Date lockTime;
    Show show;
    User lockedBy;
    int timeout;
    public boolean isLockExpired() {
        final Instant lockInstant = lockTime.toInstant().plusSeconds(timeout);
        final Instant currentInstant = new Date().toInstant();
        return lockInstant.isBefore(currentInstant);
    }
}
