package org.example.service;

import lombok.NonNull;
import org.example.model.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieService {
    private final Map<Integer, Movie> movies;
    AtomicInteger movieCounter = new AtomicInteger(0);
    public MovieService() {
        this.movies = new HashMap<>();
    }

    public Movie getMovie(@NonNull final Integer movieId) throws Exception {
        if (!movies.containsKey(movieId)) {
            throw new Exception("movies with "+movieId+" not found");
        }
        return movies.get(movieId);
    }

    public int createMovie(@NonNull final String movieName) {
        int movieId = movieCounter.incrementAndGet();
        Movie movie = new Movie(movieName,movieId);
        movies.put(movieId, movie);
        return movieId;
    }
}
