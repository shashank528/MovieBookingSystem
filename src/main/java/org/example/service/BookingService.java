package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.Factory.PaymentStrategyFactory;
import org.example.enums.BookingStatus;
import org.example.model.*;
import org.example.provider.IPaymentStrategy;
import org.example.provider.ISeatLockProvider;
import org.example.provider.UpiPaymentStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@AllArgsConstructor
@Getter
public class BookingService {
    ISeatLockProvider seatLockProvider;
    IPaymentStrategy paymentStrategy;
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
        IPaymentStrategy paymentStrategy = choosePaymentStrategy("upi");
        confirmBooking(bookingId,show,new UpiPaymentStrategy());
        System.out.println("booking succesfull "+booking);
        return bookingId;
    }

    private IPaymentStrategy choosePaymentStrategy(String key) {
       return  PaymentStrategyFactory.createPaymentStrategy(key);
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
    public void confirmBooking(int bookingId, Show show, IPaymentStrategy paymentStrategy) throws Exception {
        Booking booking = bookingMap.getOrDefault(show, Collections.emptyMap()).get(bookingId);

        if (booking == null) {
            throw new Exception("Booking not found");
        }

        if (booking.getBookingStatus() != BookingStatus.CREATED) {
            throw new Exception("Booking is not in a confirmable state");
        }
        // Use provided payment strategy to process the paymenchoose
        boolean paymentSuccess = paymentStrategy.processPayment(booking);

        if (!paymentSuccess) {
            throw new Exception("Payment failed");
        }
        // Update booking status
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        System.out.println("Booking confirmed with ID: " + bookingId);
    }

}
