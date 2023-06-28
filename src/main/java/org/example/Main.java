package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Main class.
 * <p>
 * Run me to run the service
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Main {

	/**
	 * Java 101
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}