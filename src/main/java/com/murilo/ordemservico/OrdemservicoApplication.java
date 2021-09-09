package com.murilo.ordemservico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdemservicoApplication {

	/*
	 * Utilizar esse campo para primeiras injeções deteste das classes:
	 * public class OsApplication implements CommandLineRunner {
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(OrdemservicoApplication.class, args);
	}

}
