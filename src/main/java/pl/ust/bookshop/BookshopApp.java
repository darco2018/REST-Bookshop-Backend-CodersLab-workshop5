package pl.ust.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@Import({SecurityConfig.class})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class BookshopApp {
	
	
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BookshopApp.class, args);
		System.err.println(ctx); // AnnotationConfigServletWebServerApplicationContext
	}
	
	/* UNCOMMENT ON THE FIRST RUN (PersistenceContext not loaded when @WebMvcTest is used in tests)
	 
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

