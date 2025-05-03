package org.example;

import org.example.api.BookingController;
import org.example.api.MovieController;
import org.example.api.ShowController;
import org.example.api.TheaterController;
import org.example.model.Movie;
import org.example.model.Seat;
import org.example.model.User;
import org.example.provider.ISeatLockProvider;
import org.example.provider.InMemorySeatLockProvider;
import org.example.service.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        TheaterService theaterService = new TheaterService();
        TheaterController theaterController = new TheaterController(theaterService);
        int theaterid =theaterController.createTheater("seasons mall");
        System.out.println("theater created "+theaterid);
        int screenId = theaterController.createScreenInTheater("screen A",theaterid);
        System.out.println("screen created "+screenId);
        int seatid1   = theaterController.createSeatInScreen(screenId,1,1,theaterid);
        int seatid2   = theaterController.createSeatInScreen(screenId,1,2,theaterid);
        int seatid3   = theaterController.createSeatInScreen(screenId,1,3,theaterid);
        int seatid4   = theaterController.createSeatInScreen(screenId,1,4,theaterid);
        int seatid5   = theaterController.createSeatInScreen(screenId,1,5,theaterid);
        int seatid6   = theaterController.createSeatInScreen(screenId,1,6,theaterid);
        int seatid7   = theaterController.createSeatInScreen(screenId,1,7,theaterid);
        MovieService movieService = new MovieService();
        MovieController movieController = new MovieController(movieService);
        int movideId = movieService.createMovie("mai hoo na");
        ShowService showService = new ShowService();
        SeatAvailabilityService seatAvailabilityService = new SeatAvailabilityService();
        ISeatLockProvider seatLockProvider = new InMemorySeatLockProvider();
        BookingService bookingService = new BookingService(seatLockProvider);
        ShowController showController = new ShowController(theaterService,showService,seatAvailabilityService,movieService,bookingService);
        Movie movie = movieService.getMovie(movideId);
        int showId = showController.createShow(movideId,screenId,new Date(),120,theaterid);

        BookingController bookingController = new BookingController(bookingService,showService,theaterService);
        User user1 = new User(1,"shashank","shashank@gmail.com");
        User user2 = new User(2,"shekhar","shashank631@gmail.com");
        User user3 = new User(3,"ragini","meragini07@gmai.com");
        Set<Seat> availableSeats = showController.getAvailableSeat(showService.getShow(showId));
        System.out.println(availableSeats);
        bookingController.createBooking(user1,showId, List.of(1,2),theaterid);
        Set<Seat> availableSeatsafter = showController.getAvailableSeat(showService.getShow(showId));
        System.out.println(availableSeatsafter);
        Thread t1 = new Thread()
        {
            public void run()
            {
                try {
                    bookingController.createBooking(user2,showId,List.of(3,4,5),theaterid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread()
        {
            public void run()
            {
                try {
                    bookingController.createBooking(user3,showId,List.of(6,7),theaterid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();

        t1.start();

        t1.join();
        t2.join();


    }
}
