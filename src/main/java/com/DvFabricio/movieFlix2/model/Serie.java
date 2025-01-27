package com.DvFabricio.movieFlix2.model;

import com.DvFabricio.movieFlix2.service.QueryChatgpt;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;

    @Enumerated(EnumType.STRING)
    private Category genre;
    private String actors;
    private String poster;
    private String synopsis;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Episode>episodes = new ArrayList<>();

    public Serie(){
    }

    public Serie(SerieData serieData){
        this.title = serieData.title();
        this.totalSeasons = serieData.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(serieData.rating())).orElse(0);
        this.genre = Category.fromString(serieData.genre().split(",")[0].trim());
        this.actors = serieData.actors();
        this.poster = serieData.poster();
        this.synopsis = QueryChatgpt.getTranslation(serieData.synopsis()).trim();

    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(e -> e.setSerie(this));
        this.episodes = episodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return
                "genre=" + genre +
                " , title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", review=" + rating +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", episodes='" + episodes + '\'' ;
    }
}
