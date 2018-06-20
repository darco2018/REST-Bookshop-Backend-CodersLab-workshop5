package pl.ust.bookshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookshopBackApplication {
	
	private static final Logger log = LoggerFactory.getLogger(BookshopBackApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookshopBackApplication.class, args);
	}
	
}

