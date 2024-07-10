package com.example.demo.controllers;

import com.example.demo.models.Movie;
import com.example.demo.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getById(@PathVariable("id") Long id) {
        return movieService.getById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByName(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "name") String name
    ) {
        return movieService.getByPageByName(page, limit, name);
    }

    @GetMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByRating(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "rating.kp") String rating
    ) {
        return movieService.getByPageByRating(page, limit, rating);
    }

    @GetMapping("/genre")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByGenre(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "genres.name") String genre
    ) {
        return movieService.getByPageByGenre(page, limit, genre);
    }

}
