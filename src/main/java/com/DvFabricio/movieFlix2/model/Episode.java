package com.DvFabricio.movieFlix2.model;

import java.time.LocalDate;


public class Episode {

    private Integer seasons;
    private String title;
    private Integer episodeNumber;
    private Double review;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.seasons = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.numberEpisode();
        this.review = Double.valueOf(episodeData.review());
        this.releaseDate = LocalDate.parse(episodeData.releaseDate());
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

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
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
                        ", review=" + review +
                        ", releaseDate=" + releaseDate ;
    }
}
