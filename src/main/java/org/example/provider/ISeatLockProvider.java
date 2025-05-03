package org.example.provider;

import org.example.model.Seat;
import org.example.model.Show;
import org.example.model.User;

import java.util.List;

public interface ISeatLockProvider {
    public boolean validateLock(User user, Show show, List<Seat> seatIds);
    public void lockSeat(User user, Show show, List<Seat> seatIds) throws Exception;
    public void unlockSeat(User user, Show show, List<Seat> seatIds);
    public List<Seat> getLockedSeats(final Show show);


}
