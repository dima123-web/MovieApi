package com.example.demo.controllers;


import com.example.demo.models.Movie;
import com.example.demo.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MovieController.class)
class MovieControllerTest {
    private static long id;
    private static int page;
    private static int limit;
    private static Movie mockMovie;
    private static List<Movie> mockMovieList;
    private static String movieName;
    private static String mockMovieAsJsonString;
    private static String mockMovieListAsJsonString;

    private static String rating;
    private static String genre;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        page = 1;
        limit = 1;
        id = 1L;
        movieName = "Форсаж";
        rating = "7";
        genre = "триллер";

        mockMovie = new Movie();
        mockMovie.setId(id);
        mockMovie.setName(movieName);
        mockMovieAsJsonString = objectMapper.writeValueAsString(mockMovie);

        mockMovieList = new ArrayList<>(List.of(mockMovie));
        mockMovieListAsJsonString = objectMapper.writeValueAsString(mockMovieList);
    }

    @Test
    void getById() throws Exception {
        when(movieService.getById(anyLong())).thenReturn(mockMovie);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMovieAsJsonString))
                .andExpect(status().isOk());

        verify(movieService).getById(id);
    }

    @Test
    void getByPageByName() throws Exception {
        when(movieService.getByPageByName(anyInt(), anyInt(), anyString())).thenReturn(mockMovieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/search")
                        .param("name", String.valueOf(movieName))
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMovieListAsJsonString))
                .andExpect(status().isOk());

        verify(movieService).getByPageByName(page, limit, movieName);
    }

    @Test
    void getByPageByRating() throws Exception {
        when(movieService.getByPageByRating(anyInt(), anyInt(), anyString())).thenReturn(mockMovieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/rating")
                        .param("rating.kp", String.valueOf(rating))
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMovieListAsJsonString))
                .andExpect(status().isOk());

        verify(movieService).getByPageByRating(page, limit, rating);
    }

    @Test
    void getByPageByGenre() throws Exception {
        when(movieService.getByPageByGenre(anyInt(), anyInt(), anyString())).thenReturn(mockMovieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/genre")
                        .param("genres.name", String.valueOf(genre))
                        .param("page", String.valueOf(page))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMovieListAsJsonString))
                .andExpect(status().isOk());

        verify(movieService).getByPageByGenre(page, limit, genre);
    }

}
