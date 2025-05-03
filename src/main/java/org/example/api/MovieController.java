package org.example.api;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.model.Movie;
import org.example.service.MovieService;
@AllArgsConstructor
public class MovieController {
    MovieService movieService;
    public int createMovie(@NonNull final String movieName)
    {
        return movieService.createMovie(movieName);
    }
}
