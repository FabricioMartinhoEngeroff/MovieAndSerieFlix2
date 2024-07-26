package com.DvFabricio.movieFlix2.contoller;

import com.DvFabricio.movieFlix2.dto.EpisodeDTO;
import com.DvFabricio.movieFlix2.dto.SerieDTO;
import com.DvFabricio.movieFlix2.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieContoller {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> getSeries() {
        return service.getAllSerie();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Serie() {
        return service.getTop5Serie();

    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getRecentReleases(){
        return service.getRecentReleases();

    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id){
        return  service.getById(id);
    }

    @GetMapping("/{id}/temporadas/todos")
    public List<EpisodeDTO> getAllSeason(@PathVariable Long id){
        return service.getAllSeason(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Long number){
        return service.getSeasonByNumber(id, number);

    }

    @GetMapping("category/{genreName}/")
    public List<SerieDTO> getSerieByCategory(@PathVariable String genreName){
        return  service.getSerieByCategory(genreName);
    }





}
