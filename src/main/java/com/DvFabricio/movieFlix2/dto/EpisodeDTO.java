package com.DvFabricio.movieFlix2.dto;

import com.DvFabricio.movieFlix2.model.Serie;

import java.time.LocalDate;

public record EpisodeDTO(
        Long id,
        Integer seasons,
        String title,
        Integer episodeNumber,
        Double rating,
        LocalDate releaseDate,
        Serie serie
) {}
