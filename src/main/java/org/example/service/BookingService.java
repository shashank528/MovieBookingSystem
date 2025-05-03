package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.enums.BookingStatus;
import org.example.model.*;
import org.example.provider.ISeatLockProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@AllArgsConstructor
@Getter
public class BookingService {
    ISeatLockProvider seatLockProvider;
    Map<Show,Map<Integer,Booking>> bookingMap = new ConcurrentHashMap<>();
    AtomicInteger bookingCounter = new AtomicInteger(0);

    public BookingService(ISeatLockProvider seatLockProvider) {
        this.seatLockProvider=seatLockProvider;
    }

    public int createBooking(User user, Show show, List<Seat> seats) throws Exception {
        if(anySeatAlreadyBooked(user,show,seats))
        {
            throw new Exception("selected seat is booked");
        }
        seatLockProvider.lockSeat(user,show,seats);
        int bookingId = bookingCounter.incrementAndGet();
        Booking booking = new Booking(bookingId,user,show,seats, BookingStatus.CREATED);
        bookingMap.putIfAbsent(show,new ConcurrentHashMap<>());
        bookingMap.get(show).putIfAbsent(bookingId,booking);
        System.out.println("booking succesfull "+booking);
        return bookingId;
    }
    public Set<Seat> getBookedSeat(Show show)
    {
        if(!bookingMap.containsKey(show))
        {
            System.out.println("alll seats are available");
            return new HashSet<>();
        }
        List<Booking> getAllBoookedSeatforShow = new ArrayList<>(bookingMap.get(show).values());
        Set<Seat> alreadyBookedSeat = new HashSet<>();
        List<Seat> listofbookedseat = new ArrayList<>();
        for(Booking booking:getAllBoookedSeatforShow)
        {
            if(booking.getBookingStatus()==BookingStatus.CONFIRMED)
            {
                for(Seat seat:booking.getSeat())
                {
                    alreadyBookedSeat.add(seat);
                    listofbookedseat.add(seat);
                }
            }
        }
        return alreadyBookedSeat;
    }
    public boolean anySeatAlreadyBooked(User user, Show show, List<Seat> seats) {
        if(!bookingMap.containsKey(show))
        {
            System.out.println("alll seats are available");
            return false;
        }
        List<Booking> getAllBoookedSeatforShow = new ArrayList<>(bookingMap.get(show).values());
        Set<Seat> alreadyBookedSeat = getBookedSeat(show);
        for(Seat seat:seats)
        {
            if(alreadyBookedSeat.contains(seat))
            {
                return true;
            }
        }
        return false;
    }
    public Set<Seat> getLockedSeat(Show show)
    {
        List<Seat> lockedSeats = seatLockProvider.getLockedSeats(show);
        return new HashSet<>(lockedSeats);
    }
}
