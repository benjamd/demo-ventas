package com.recicladosplasticos.springboot.app;

import org.modelmapper.ModelMapper; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootRecicladoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRecicladoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
