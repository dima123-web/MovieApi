package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieApi {

    @JsonProperty("docs")
    private List<Movie> movies;

    private int total;

    private int limit;

    private int page;

    private int pages;
}
