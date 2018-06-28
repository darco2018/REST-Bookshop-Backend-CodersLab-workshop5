package pl.ust.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/*
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;*/

@SpringBootApplication
public class BookshopApp {
	
/* //UNCOMMENT ON THE FIRST RUN TO POPULATE DB (Commented off because PersistenceContext not loaded when @WebMvcTest is used in tests)
	@PersistenceContext
	private EntityManager entityManager;

	@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	class DBPopulatorRunner implements ApplicationRunner {

		@Override
		@Transactional // !!!
		public void run(ApplicationArguments args) throws Exception {
			DBPopulator.populatePublishers(entityManager);
			DBPopulator.populateAuthors(entityManager);
			DBPopulator.populateBooks(entityManager);
		}

	}*/
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BookshopApp.class, args);
		System.err.println(ctx); // AnnotationConfigServletWebServerApplicationContext
	}
	
	
	 
	 
	
	
	
	
	/*	
	@Component
	class MyCLRunner1 implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
						
			
		}
	}*/
	
}

