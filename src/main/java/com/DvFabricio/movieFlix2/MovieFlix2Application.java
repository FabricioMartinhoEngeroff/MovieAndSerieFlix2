package com.DvFabricio.movieFlix2;

import com.DvFabricio.movieFlix2.man.Man;
import com.DvFabricio.movieFlix2.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieFlix2Application {

    public static void main(String[] args) {
        SpringApplication.run(MovieFlix2Application.class, args);
    }
}
