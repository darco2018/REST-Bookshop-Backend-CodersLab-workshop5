package pl.ust.bookshop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookshopApp {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BookshopApp.class, args);
	}
	
	/* UNCOMMENT ON THE FIRST RUN
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
		
	} 
	*/
	
	
	/*	
	@Component
	class MyCLRunner1 implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
						
			
		}
	}*/
	
}

