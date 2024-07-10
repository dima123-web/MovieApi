package com.example.demo.services;

import com.example.demo.Client.KinopoiskApiClient;
import com.example.demo.models.Movie;
import com.example.demo.models.MovieApi;
import com.example.demo.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    private long id;
    private int page;
    private int limit;
    private Movie mockMovie;
    private MovieApi mockApiMoviePage;
    private List<Movie> mockMovieList;
    private String movieName;
    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private KinopoiskApiClient kinopoiskApiClientRouter;

    @BeforeEach
    void setUp() {
        page = 1;
        limit = 1;
        id = 4647040;
        movieName = "Король и Шут";

        mockMovie = new Movie();
        mockMovie.setId(id);
        mockMovie.setName(movieName);

        mockMovieList = new ArrayList<>(List.of(mockMovie));

        mockApiMoviePage = new MovieApi();
        mockApiMoviePage.setMovies(mockMovieList);
    }

    @Test
    void getByIdShouldReturnModelsFromDb() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(mockMovie));

        Movie movie = movieService.getById(id);

        assertEquals(mockMovie, movie);
        assertNotNull(movie);

        verify(movieRepository).findById(id);
        verify(kinopoiskApiClientRouter, never()).findById(id);
    }

    @Test
    void getByIdShouldReturnModelsFromApi() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(kinopoiskApiClientRouter.findById(anyLong())).thenReturn(Optional.of(mockMovie));

        Movie movie = movieService.getById(id);

        assertNotNull(movie);
        assertEquals(mockMovie, movie);

        verify(movieRepository).findById(id);
        verify(kinopoiskApiClientRouter).findById(id);
    }

    @Test
    void getByNameByPageShouldReturnModelsListFromApi() {
        when(kinopoiskApiClientRouter.findByPageByName(anyInt(), anyInt(), anyString())).thenReturn(mockApiMoviePage);

        List<Movie> movieList = movieService.getByPageByName(page, limit, movieName);

        assertEquals(mockMovieList, movieList);
        assertNotNull(movieList);

        verify(kinopoiskApiClientRouter).findByPageByName(page, limit, movieName);
    }
}
