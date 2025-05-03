package org.example.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.model.*;
import org.example.service.BookingService;
import org.example.service.ShowService;
import org.example.service.TheaterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Getter
public class BookingController {
    BookingService bookingService;
    ShowService showService;
    TheaterService theaterService;
    public int createBooking(User user, int showid, List<Integer> seatIds,int theaterId) throws Exception {
        Show show = showService.getShow(showid);
        Theater theater = show.getTheater();
        Screen screen = show.getScreen();
        Map<Integer, Seat> seatIdToSeatMap = theaterService.getAllSeatForShow(theater.getTheaterId(),screen.getScreenId());
        List<Seat> seatToBeBooked = new ArrayList<>();
        for(int seaId:seatIds)
        {
            if(seatIdToSeatMap.containsKey(seaId))
            {
                seatToBeBooked.add(seatIdToSeatMap.get(seaId));
            }
        }
       return bookingService.createBooking(user,show,seatToBeBooked);
    }
}
