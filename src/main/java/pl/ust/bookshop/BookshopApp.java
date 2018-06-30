package pl.ust.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import pl.ust.bookshop.security.ImMemorySecurityConfig;
import pl.ust.bookshop.security.DatabaseSecurityConfig;

@SpringBootApplication
@Import({DatabaseSecurityConfig.class})
public class BookshopApp {
	
	
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BookshopApp.class, args);
		System.err.println(ctx); // AnnotationConfigServletWebServerApplicationContext
	}
	
	/* UNCOMMENT ON THE FIRST RUN TO POPULATE THE DATABASE
	  (PersistenceContext not loaded when @WebMvcTest is used in tests)
	 
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

