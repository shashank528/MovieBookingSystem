package org.example.service;

import lombok.NonNull;
import org.example.model.*;

import java.awt.*;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowService {
    //create a map to store key as theater id value as a map where key willl be showid, value as map where key will be seatid and value will be seat;
    Map<Integer,Show> shows = new ConcurrentHashMap<>();
    Map<Integer,Map<Integer,Map<Integer, Seat>>> seattoShow = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger(0);
    public int createShow(@NonNull final Movie movie, @NonNull final Screen screen, Date startTime, Integer duration, Theater theater) {
        int showId = counter.incrementAndGet();
        int screenId = screen.getScreenId();
        int theaterId = theater.getTheaterId();
        Show show = new Show(showId,screen,theater,movie,startTime,duration);
        shows.put(showId,show);
        return showId;
    }
    public Show getShow(Integer id) throws Exception {
        if(!shows.containsKey(id))
        {
            throw new Exception("show doesnot exist");
        }
        return shows.get(id);
    }
}
