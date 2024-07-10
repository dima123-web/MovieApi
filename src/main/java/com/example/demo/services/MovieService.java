package com.example.demo.services;

import com.example.demo.Client.KinopoiskApiClient;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {

    private final KinopoiskApiClient kinopoiskApiClient;
    private final MovieRepository movieRepository;

    public Movie getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .or(() -> findWithApiById(id))
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
        movieRepository.save(movie);
        return movie;
    }

    public List<Movie> getByPageByName(int page, int limit, String name) {
        List<Movie> movieList = findWithApiByPageByName(page, limit, name);
        movieRepository.saveAll(movieList);
        return movieList;
    }

    public List<Movie> getByPageByRating(int page, int limit, String rating) {
        List<Movie> movieList = findWithApiByPageByRating(page, limit, rating);
        movieRepository.saveAll(movieList);
        return movieList;
    }

    public List<Movie> getByPageByGenre(int page, int limit, String genre) {
        List<Movie> movieList = findWithApiByPageByGenre(page, limit, genre);
        movieRepository.saveAll(movieList);
        return movieList;
    }

    private Optional<Movie>findWithApiById(Long id) {
        Optional<Movie> movie = Optional.empty();
        try {
            movie = kinopoiskApiClient.findById(id);
        } catch (FeignException e) {
            // обработка, если фильм не найден
        }
        return movie;
    }

    private List<Movie> findWithApiByPageByName(int page, int limit, String name) {
        return kinopoiskApiClient.findByPageByName(page, limit, name).getMovies();
    }

    private List<Movie> findWithApiByPageByRating(int page, int limit, String rating) {
        return kinopoiskApiClient.findByPageByRatingKp(page, limit, rating).getMovies();
    }

    private List<Movie> findWithApiByPageByGenre(int page, int limit, String genre) {
        return kinopoiskApiClient.findByPageByRGenres(page, limit, genre).getMovies();
    }

}
