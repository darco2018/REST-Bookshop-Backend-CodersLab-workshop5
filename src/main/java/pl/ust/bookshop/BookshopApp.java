package pl.ust.bookshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import pl.ust.bookshop.author.AuthorService;
import pl.ust.bookshop.book.BookService;
import pl.ust.bookshop.publisher.PublisherService;

@SpringBootApplication
public class BookshopApp {
	
	private static final Logger log = LoggerFactory.getLogger(BookshopApp.class);

	public static void main(String[] args) {
		SpringApplication.run(BookshopApp.class, args);
	}
	/*
	@Bean 
	ApplicationRunner populate (PublisherService publisherService, BookService bookService, AuthorService authorService) { // ApplicationArguments args
		log.info("-----@@@-----AppRunner: Runs early, after creation of databases-----------------");
		
		//DBPopulator.populatePublishers(publisherService);
		//DBPopulator.populateAuthors(authorService);
		//DBPopulator.populateBooks(bookService, publisherService, authorService);
		return null ; 
	}*/
	
	// TODO remove if not needed
	@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	class MyCLRunner1 implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
			log.info("---------CLRunner1 - at the end of startup");
		}
	}

	@Component
	@Order(2)
	class MyCLRunner2 implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
			log.info("---------CLRunner2 - at the end of startup");
		}
	}
	
}

