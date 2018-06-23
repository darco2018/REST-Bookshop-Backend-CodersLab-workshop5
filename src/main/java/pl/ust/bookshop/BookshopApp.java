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

import pl.ust.bookshop.aaa.Address;
import pl.ust.bookshop.aaa.Person;
import pl.ust.bookshop.author.AuthorService;
import pl.ust.bookshop.book.BookService;
import pl.ust.bookshop.publisher.PublisherService;

@SpringBootApplication
public class BookshopApp {
	
	private static final Logger log = LoggerFactory.getLogger(BookshopApp.class);
	
	private javax.persistence.EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(BookshopApp.class, args);
	}
	
	@Bean 
	@Order(Ordered.HIGHEST_PRECEDENCE)
	ApplicationRunner populate () { // ApplicationArguments args
		
		return args -> {
			
			Person person1 = new Person( "ABC-123" );
			Person person2 = new Person( "DEF-456" );

			Address address1 = new Address( "12th Avenue", "12A", "4005A" );
			Address address2 = new Address( "18th Avenue", "18B", "4007B" );

			entityManager.persist( person1 );
			entityManager.persist( person2 );

			entityManager.persist( address1 );
			entityManager.persist( address2 );

			person1.addAddress( address1 );
			person1.addAddress( address2 );

			person2.addAddress( address1 );

			entityManager.flush();

			log.info( "Removing address" );
			person1.removeAddress( address1 );
			
			log.info("-----@@@-----AppRunner: Runs early, after creation of databases-----------------");
		} ; 
	}
	
	// TODO remove if not needed
	@Component
	@Order(1)
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

