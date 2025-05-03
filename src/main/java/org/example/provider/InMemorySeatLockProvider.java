package org.example.provider;

import org.example.model.Seat;
import org.example.model.SeatLock;
import org.example.model.Show;
import org.example.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemorySeatLockProvider implements ISeatLockProvider{
    Map<Show, Map<Seat, SeatLock>> locked = new ConcurrentHashMap<>();
    int lockTimeout = 60;
    @Override
    public boolean validateLock(User user, Show show, List<Seat> seats) {
        Map<Seat, SeatLock> seatLocks = locked.get(show);
        if (seatLocks == null) return false;
        synchronized (seatLocks) {
            for(Seat seat:seats) {
                SeatLock lock = seatLocks.get(seat);
                return lock != null && !lock.isLockExpired() && lock.getLockedBy().equals(user);
            }
        }
        return true;
    }

    @Override
    public void lockSeat(User user, Show show, List<Seat> seats) throws Exception {
        Map<Seat,SeatLock> seatLocks = locked.computeIfAbsent(show,s->new ConcurrentHashMap<>());
        synchronized (seatLocks)
        {
            for(Seat s:seats)
            {
                if(seatLocks.containsKey(s))
                {
                    SeatLock seatLock = seatLocks.get(s);
                    if(!seatLock.isLockExpired())
                    {
                        if(seatLock.getLockedBy().getUserId()!= user.getUserId())
                        throw new Exception("selected seat is already locked bu other user");
                    }
                }
            }
            Date now = new Date();
            for (Seat seat : seats) {
                SeatLock lock = new SeatLock(seat, now, show, user, lockTimeout);
                seatLocks.put(seat, lock);
            }
        }
    }

    @Override
    public void unlockSeat( final User user,final Show show,final List<Seat> seats ) {
        Map<Seat, SeatLock> seatLocks = locked.get(show);
        if (seatLocks == null) return;
        synchronized (seatLocks) {
            for (Seat seat : seats) {
                SeatLock lock = seatLocks.get(seat);
                if (lock != null && lock.getLockedBy().equals(user)) {
                    seatLocks.remove(seat);
                }
            }
        }
    }
    public List<Seat> getLockedSeats(final Show show) {
        Map<Seat, SeatLock> seatLocks = locked.get(show);
        if (seatLocks == null) {
            return Collections.emptyList();
        }
        synchronized (seatLocks) {
            return seatLocks.entrySet().stream()
                    .filter(entry -> !entry.getValue().isLockExpired())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}
