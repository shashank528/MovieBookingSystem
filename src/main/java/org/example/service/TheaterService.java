package org.example.service;

import org.example.model.Screen;
import org.example.model.Seat;
import org.example.model.Theater;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TheaterService {
     //create a map to store key as theater id value as a map where key willl be showid, value as map where key will be seatid and value will be seat;
    Map<Integer,Map<Integer,Map<Integer,Seat>>> theaterIdToScreenIdToSeatIdToSeatMap = new ConcurrentHashMap<>();
    Map<Integer,Theater> theaterIdToTheaterMap = new ConcurrentHashMap<>();
    Map<Integer,Screen> screenIdToScreenMap = new ConcurrentHashMap<>();
    Map<Integer,Screen> seatIdToSeat = new ConcurrentHashMap<>();
    AtomicInteger theaterCouner = new AtomicInteger(0);
     AtomicInteger screencounter = new AtomicInteger(0);
    AtomicInteger seatCounter = new AtomicInteger(0);

    public int createTheater(String theaterName) {
        int theaterId = theaterCouner.incrementAndGet();
        Theater theater = new Theater(theaterId,theaterName);
        theaterIdToTheaterMap.putIfAbsent(theaterId,theater);
        return theaterId;
    }

    public int createScreenInTheater(String screeName, int  theaterId) throws Exception {
        Theater theater = theaterIdToTheaterMap.getOrDefault(theaterId,null);
        if(null!=theater)
        {
            int screenId = screencounter.incrementAndGet();
            Screen screen = new Screen(screenId,screeName,theater);
            theater.addScreen(screen);
            screenIdToScreenMap.putIfAbsent(screenId,screen);
            return screenId;
        }
        else
        {
            throw new Exception("theater does not exist");
        }

    }

    public Screen getScreen(int  screenId) {
        return screenIdToScreenMap.get(screenId);
    }
    public Theater getTheater(int theaterId)
    {
        return theaterIdToTheaterMap.get(theaterId);
    }

    public Integer createSeatInScreen(Screen screen, Integer rowNo, Integer seatNo,Theater theater) {
            int seatId = seatCounter.incrementAndGet();
            Seat seat = new Seat(seatId,rowNo,seatNo);
            theaterIdToScreenIdToSeatIdToSeatMap.computeIfAbsent(theater.getTheaterId(), k -> new ConcurrentHashMap<>())
                .computeIfAbsent(screen.getScreenId(), k -> new ConcurrentHashMap<>())
                .put(seatId, seat);
            return seatId;
    }

    public Map<Integer, Seat> getAllSeatForShow(int theaterId, int screenId) {
        return theaterIdToScreenIdToSeatIdToSeatMap.get(theaterId).get(screenId);
    }
}
