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
@CrossOrigin(origins = "http://localhost:5173/")
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

    // использую
    @GetMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByRating(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "16") int limit,
            @RequestParam(value = "rating.kp", required = false, defaultValue = "9-10") String rating
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

    // новые методы
    @GetMapping("/popularity")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByPopularity(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "16") int limit,
            @RequestParam(value = "lists", required = false, defaultValue = "top250") String lists
    ) {
        return movieService.getByPageByPopularity(page, limit, lists);
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getByPageByNew(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "16") int limit,
            @RequestParam(value = "notNullFields", required = false, defaultValue = "poster.url") String poster,
            @RequestParam(value = "year", required = false, defaultValue = "2024") String year
    ) {
        return movieService.getByPageByNew(page, limit, poster,year);
    }

}
