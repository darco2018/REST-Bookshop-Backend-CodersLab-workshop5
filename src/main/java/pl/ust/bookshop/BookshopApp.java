package pl.ust.bookshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookshopApp {
	
	private static final Logger log = LoggerFactory.getLogger(BookshopApp.class);

	public static void main(String[] args) {
		SpringApplication.run(BookshopApp.class, args);
	}
	
}

