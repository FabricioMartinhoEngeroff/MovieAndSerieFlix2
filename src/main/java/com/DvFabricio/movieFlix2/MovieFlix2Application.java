package com.DvFabricio.movieFlix2;

import com.DvFabricio.movieFlix2.model.DataSerie;
import com.DvFabricio.movieFlix2.service.ConsumeApi;
import com.DvFabricio.movieFlix2.service.ConvertData;
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
		var consumoApi = new ConsumeApi();
		var json = consumoApi.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		ConvertData converter = new ConvertData();
		DataSerie data = converter.getData(json, DataSerie.class);
		System.out.println(data);

	}
}
