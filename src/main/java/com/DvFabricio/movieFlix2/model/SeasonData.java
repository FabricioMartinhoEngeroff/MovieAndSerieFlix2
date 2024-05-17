package com.DvFabricio.movieFlix2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Season")Integer numberEpisode,
                         @JsonAlias("Episode")List<EpisodeData> episodes) {

}
