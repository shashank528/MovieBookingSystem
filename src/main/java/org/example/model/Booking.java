package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.enums.BookingStatus;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class Booking {
    final int bookingId;
    final User user;
    final Show show;
    List<Seat> seats;
    BookingStatus bookingStatus;
    public List<Seat> getSeat()
    {
        return seats;
    }

}
