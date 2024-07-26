package com.DvFabricio.movieFlix2.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seasons;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    @ManyToOne
    private Serie serie;

    public Episode(){
    }

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.seasons = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.numberEpisode();

        try {
            this.rating = Double.valueOf(episodeData.rating());
        }catch (NumberFormatException ex){
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodeData.releaseDate());
        }catch (DateTimeParseException ex){
            this.releaseDate = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "seasons=" + seasons +
                        ", title='" + title + '\'' +
                        ", episodeNumber=" + episodeNumber +
                        ", review=" + rating +
                        ", releaseDate=" + releaseDate ;
    }
}
