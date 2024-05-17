package com.DvFabricio.movieFlix2;

import com.DvFabricio.movieFlix2.man.Man;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieFlix2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MovieFlix2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Man man = new Man();
        man.displayMenu();
    }
}