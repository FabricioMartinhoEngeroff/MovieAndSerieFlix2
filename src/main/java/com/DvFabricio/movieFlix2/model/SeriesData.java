package com.DvFabricio.movieFlix2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("title") String title,
                        @JsonAlias("totalSeasons")Integer totalSeasons,
                        @JsonAlias("imdbRating") String review,
                        @JsonAlias("imdbVotes") String votes) {
}
