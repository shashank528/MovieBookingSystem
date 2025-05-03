package org.example.service;

import org.example.model.Seat;
import org.example.model.Show;

import java.util.List;

public class SeatAvailabilityService {

    public List<Seat> getAvailableSeat(Show show) {
        List<Seat> allSeat =show.getScreen().getSeats();
        return allSeat;
    }
}
