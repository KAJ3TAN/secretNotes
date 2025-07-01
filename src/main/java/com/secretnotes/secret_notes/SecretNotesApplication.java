//Główny plik uruchamiający aplikację – inicjuje i startuje cały projekt Spring Boot.

package com.secretnotes.secret_notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecretNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretNotesApplication.class, args);
	}

}
