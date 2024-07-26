package com.DvFabricio.movieFlix2.repository;

import com.DvFabricio.movieFlix2.model.Category;
import com.DvFabricio.movieFlix2.model.Episode;
import com.DvFabricio.movieFlix2.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, Double rating);

    List<Serie> findTop5ByOrderByRatingDesc();

    List<Serie> findByGenre(Category category);

    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.rating >= :rating ")
    List<Serie>seriesBySeasonAndRating(int totalSeasons, double rating);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:partEpisode%")
    List<Episode> episodesPerPart(String partEpisode);


    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.rating DESC")
    List<Episode> findTop5EpisodeByOrderByRatingDesc(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR(e.releaseDate) >= :releaseYear")
    List<Episode> episodesbySeriesAndYear(Serie serie, int releaseYear);

    @Query("SELECT s FROM Serie s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.releaseDate) DESC")
    List<Serie> findTop5ByOrderByLatestEpisodeReleaseDateDesc();

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s.id = :id AND e.seasons = :numero")
    List<Episode> getEpisodesBySeasons(Long id, Long numero);

}
