package org.example.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.model.Screen;
import org.example.model.Theater;
import org.example.service.TheaterService;

@AllArgsConstructor
public class TheaterController {
    TheaterService theaterService;
    public int createTheater(@NonNull final String theaterName)
    {
        return theaterService.createTheater(theaterName);
    }
    public int createScreenInTheater(@NonNull final String screeName,@NonNull final int theaterId) throws Exception {
        return theaterService.createScreenInTheater(screeName,theaterId);
    }
    public int createSeatInScreen(@NonNull final int screenId,@NonNull final Integer rowNo,@NonNull final Integer seatNo,@NonNull final int theaterId)
    {
        Screen screen = theaterService.getScreen(screenId);
        Theater theater = theaterService.getTheater(theaterId);
       return theaterService.createSeatInScreen(screen,rowNo,seatNo,theater);
    }
}
