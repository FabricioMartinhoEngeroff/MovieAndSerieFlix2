package com.DvFabricio.movieFlix2.man;

import com.DvFabricio.movieFlix2.model.Episode;
import com.DvFabricio.movieFlix2.model.EpisodeData;
import com.DvFabricio.movieFlix2.model.SeasonData;
import com.DvFabricio.movieFlix2.model.SeriesData;
import com.DvFabricio.movieFlix2.service.ConsumeApi;
import com.DvFabricio.movieFlix2.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Man {

    private Scanner reader = new Scanner(System.in);
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";
    ;

    private final ConsumeApi consumer = new ConsumeApi();
    private final ConvertData converter = new ConvertData();

    public void displayMenu() {
        System.out.println("Enter the name of the series to search");
        var seriesName = reader.nextLine();
        var json = consumer.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SeriesData data = converter.getData(json, SeriesData.class);
        System.out.println(data);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = consumer.getData(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }

        seasons.forEach(System.out::println);

        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

        List<EpisodeData> episodeData = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

//        System.out.println("\n Top 5episodes");
//        episodeData.stream()
//                .filter(e -> !e.review().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("First filter(N/A) " + e))
//                .sorted(Comparator.comparing(EpisodeData::review).reversed())
//                .peek(e-> System.out.println("Ordination " + e))
//                .limit(2)
//                .peek(e-> System.out.println("limit " + e))
//                .map(e -> e.title().toUpperCase())
//                .peek(e-> System.out.println("mapping " + e))
//                .forEach(System.out::println);


        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.numberEpisode(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Write a part of the episode title");
        var titlePart = reader.next();
        Optional<Episode> episodeSearched = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titlePart.toUpperCase()))
                .findFirst();

        if (episodeSearched.isPresent()) {
            System.out.println("Episode found");
            System.out.println("Season " + episodeSearched.get().getSeasons());
        } else {
            System.out.println("Episode not found!");
        }

//        System.out.println("From What year do you want to see the episodes from? ");
//        var ano = reader.nextInt();
//
//        LocalDate searchDate = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeasons() +
//                                "Episode: " + e.getTitle() +
//                                "Release date: " + e.getReleaseDate().format(formatter)
//                ));
//   }

        Map<Integer, Double> ratingsBySeason = episodes.stream()
                .filter(e -> e.getReview() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeasons,
                        Collectors.averagingDouble(Episode::getReview)));
        System.out.println(ratingsBySeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getReview() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getReview));
        System.out.println("Everage " + est.getAverage());
        System.out.println("Best episodes "  + est.getMax());
        System.out.println("Worst episodes " + est.getMin());
        System.out.println("Quantity " + est.getCount());

    }
}
