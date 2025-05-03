package org.example.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.model.*;
import org.example.service.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ShowController {
    TheaterService theaterService;
    ShowService showService;
    SeatAvailabilityService seatAvailabilityService;
    MovieService movieService;
    BookingService bookingService;
    public Integer createShow(@NonNull final int movieId, @NonNull final int  screenId, @NonNull final Date startTime, @NonNull Integer duration, @NonNull final int theaterId) throws Exception {
        Screen screen = theaterService.getScreen(screenId);
        Movie movie = movieService.getMovie(movieId);
        Theater theater = theaterService.getTheater(theaterId);
        return showService.createShow(movie,screen,startTime,duration,theater);
    }
    public Set<Seat> getAvailableSeat(@NonNull final Show show)
    {
        Set<Seat> bookedSeat = bookingService.getBookedSeat(show);
        Set<Seat> lockedSeat = bookingService.getLockedSeat(show);
        Set<Seat> totalSeat = new HashSet(show.getScreen().getSeats());
        totalSeat.remove(bookedSeat);
        totalSeat.remove(lockedSeat);
        return totalSeat;

    }
}
