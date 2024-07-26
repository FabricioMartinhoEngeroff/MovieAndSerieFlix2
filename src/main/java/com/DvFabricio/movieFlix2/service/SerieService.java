package com.DvFabricio.movieFlix2.service;

import com.DvFabricio.movieFlix2.dto.EpisodeDTO;
import com.DvFabricio.movieFlix2.dto.SerieDTO;
import com.DvFabricio.movieFlix2.model.Category;
import com.DvFabricio.movieFlix2.model.Serie;
import com.DvFabricio.movieFlix2.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {


    @Autowired
    SerieRepository repository;

    public List<SerieDTO> getAllSerie() {
        return convertData(repository.findAll());

    }


    public List<SerieDTO> getTop5Serie() {
        return convertData(repository.findTop5ByOrderByRatingDesc());

    }

    public List<SerieDTO> getRecentReleases() {
        return convertData(repository.findTop5ByOrderByLatestEpisodeReleaseDateDesc());
    }

    public SerieDTO getById(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitle(),
                    String.valueOf(s.getTotalSeasons()),
                    s.getRating(),
                    String.valueOf(s.getGenre()), //
                    s.getActors(),
                    s.getPoster(),
                    s.getSynopsis());
        }
        return null;
    }

    public List<EpisodeDTO> getAllSeason(Long id) {
        return repository.findById(id)
                .map(serie -> serie.getEpisodes().stream()
                        .map(e -> new EpisodeDTO(
                                e.getId(),
                                e.getSeasons(),
                                e.getTitle(),
                                e.getEpisodeNumber(),
                                e.getRating(),
                                e.getReleaseDate(),
                                e.getSerie()))
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Long number) {
        return repository.getEpisodesBySeasons(id, number)
                .stream()
                .map(e -> new EpisodeDTO(
                        e.getId(),
                        e.getSeasons(),
                        e.getTitle(),
                        e.getEpisodeNumber(),
                        e.getRating(),
                        e.getReleaseDate(),
                        e.getSerie()
                ))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> getSerieByCategory(String genreName) {
        Category category;
        try {
            category = Category.fromString(genreName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid category: " + genreName);
        }
        return convertData(repository.findByGenre(category));
    }

    private List<SerieDTO> convertData(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitle(),
                        String.valueOf(s.getTotalSeasons()),
                        s.getRating(),
                        String.valueOf(s.getGenre()), //
                        s.getActors(),
                        s.getPoster(),
                        s.getSynopsis()
                ))
                .collect(Collectors.toList());
    }
}

