package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Screen {
    int screenId;
    String screenName;
    Theater theater;
    List<Seat> seats;
    public Screen(int screenId,String screenName,Theater theater)
    {
        this.screenId = screenId;
        this.screenName = screenName;
        this.theater = theater;
        seats=new ArrayList<>();
    }
    public void addSeat(Seat seat)
    {
        seats.add(seat);
    }

}
