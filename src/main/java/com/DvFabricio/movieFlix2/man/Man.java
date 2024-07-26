package com.DvFabricio.movieFlix2.man;

import com.DvFabricio.movieFlix2.model.*;
import com.DvFabricio.movieFlix2.repository.SerieRepository;
import com.DvFabricio.movieFlix2.service.ConsumeApi;
import com.DvFabricio.movieFlix2.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Man {

    private Scanner read = new Scanner(System.in);
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";
    private final ConsumeApi consumer = new ConsumeApi();
    private final ConvertData converter = new ConvertData();

    private List<SerieData> seriesData = new ArrayList<>();

    private Optional<Serie> getSerie;

    private SerieRepository repository;

    private List<Serie> series = new ArrayList<>();

    public Man(SerieRepository repository) {
        this.repository = repository;
    }

    public void displayMenu() {

        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search series
                    2 - Search for episodes
                    3 - List searched series       
                    4 - Get serie by title 
                    5 - Get serie by actors 
                    6 - Get top 5 series  
                    7 - Get series by gener 
                    8 - Filter Series By Season Rating 
                    9 - Get Episode by excerpts
                    10 - Get top 5 episodes
                    11 - Get for episodes from a date
                    0 - Exit
                    """;

            System.out.println(menu);
            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    getSeriesWeb();
                    break;
                case 2:
                    getEpisodeBySeries();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    getSerieByTitle();
                    break;
                case 5:
                    getSeriesByAuthor();
                    break;
                case 6:
                    getTop5Series();
                    break;
                case 7:
                    getSeriesByGener();
                    break;
                case 8:
                    filterSeriesBySeasonRating();
                    break;
                case 9:
                    getEpisodeByExcerpts();
                    break;
                case 10:
                    getTop5Episodes();
                    break;
                case 11:
                    getEpisodesFromDate();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }


    private void getSeriesWeb() {
        SerieData data = getSerieData();
        Serie serie = new Serie(data);
        repository.save(serie);
        System.out.println(data);
    }

    private SerieData getSerieData() {
        System.out.println("Write the name of the series to search");
        var seriesName = read.nextLine();
        var json = consumer.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SerieData data = converter.getData(json, SerieData.class);
        return data;
    }

    private void getEpisodeBySeries() {
        listSearchedSeries();
        System.out.println("Choose the series by name: ");
        var serieName = read.nextLine();

        getSerie = repository.findByTitleContainingIgnoreCase(serieName);

        if (getSerie.isPresent()) {

            var foundSerie = getSerie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSerie.getTotalSeasons(); i++) {
                var json = consumer.getData(ADDRESS + foundSerie.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = converter.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.numberEpisode(), e)))
                    .collect(Collectors.toList());

            foundSerie.setEpisodes(episodes);
            repository.save(foundSerie);

        } else {
            System.out.println("Series not found!!");
        }
    }

    private void listSearchedSeries() {
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void getSerieByTitle() {
        System.out.println("Choose the series by name: ");
        var serieName = read.nextLine();

        Optional<Serie> serieGetted = repository.findByTitleContainingIgnoreCase(serieName);

        if (serieGetted.isPresent()) {
            System.out.println("Serie Date: " + serieGetted.get());
        } else {
            System.out.println("Serie not found!");
        }
    }

    private void getSeriesByAuthor() {
        listSearchedSeries();
        System.out.println("What is the name of the author you are looking for? ");
        var actorName = read.nextLine();
        System.out.println("What rating do you want? ");
        var evaluation = read.nextDouble();

        List<Serie> seriesFound = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, evaluation);
        System.out.println("Series in which: " + actorName + "has worked");
        seriesFound.forEach(s ->
                System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }


    private void getTop5Series() {
        List<Serie> topSerie = repository.findTop5ByOrderByRatingDesc();
        topSerie.forEach(s ->
                System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    private void getSeriesByGener() {
        listSearchedSeries();
        System.out.println("What series category are you looking for? ");
        var genreName = read.nextLine().trim();
        ;
        List<Serie> categoryFound = repository.findByGenre(Category.valueOf(genreName));
        System.out.println("Series in which: " + genreName + "have found");
        categoryFound.forEach(System.out::println);
    }

    private void filterSeriesBySeasonRating() {
        listSearchedSeries();
        System.out.println("Filter series up to how many seasons? ");
        Integer totalSeasons = Integer.parseInt(read.nextLine().trim());
        System.out.println("With rating based on what value? ");
        Double valueSeason = read.nextDouble();
        List<Serie> seriesFilters = repository.seriesBySeasonAndRating(totalSeasons, valueSeason);
        System.out.println("*** filtered series *** ");
        seriesFilters.forEach(f ->
                System.out.println(f.getTitle() + " -evaluation " + f.getRating()));
    }

    private void getEpisodeByExcerpts() {
        System.out.println("What is the name of the episode you are looking for? ");
        var partEpisode = read.nextLine();
        List<Episode> episodesFound = repository.episodesPerPart(partEpisode);
        episodesFound.forEach(e ->
                System.out.printf("Serie: %s Temporada %s - Episodes %s - %s\n",
                        e.getSerie().getTitle(), e.getSeasons(),
                        e.getEpisodeNumber(), e.getTitle()));
    }

    private void getTop5Episodes() {
        getSerieByTitle();

        if (getSerie.isPresent()) {
            var serie = getSerie.get();

            List<Episode> topEpisodes = repository.findTop5EpisodeByOrderByRatingDesc(serie);
            topEpisodes.forEach(e ->
                    System.out.printf("Série: %s Temporada: %s - Episódio: %s - %s\n",
                            e.getSerie().getTitle(), e.getSeasons(),
                            e.getEpisodeNumber(), e.getTitle()));
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void getEpisodesFromDate() {
        getSerieByTitle();

        if (getSerie.isPresent()) {
            var serie = getSerie.get();
            System.out.println("Write the year at the release limit");
            var releaseYear = read.nextInt();
            read.nextLine();

            List<Episode> episodesDate = repository.episodesbySeriesAndYear(serie, releaseYear);
            episodesDate.forEach(System.out::println);
        }

    }


}