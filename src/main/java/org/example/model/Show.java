package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Show {
    Integer showId;
    Screen screen;
    Theater theater;
    Movie movie;
    Date startTime;
    Integer duration;

}
