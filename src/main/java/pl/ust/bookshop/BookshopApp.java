package pl.ust.bookshop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import pl.ust.bookshop.aaa.Address;
import pl.ust.bookshop.aaa.Person;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.publisher.Publisher;

@SpringBootApplication
public class BookshopApp {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/*@Autowired
	private PopulationService ps;*/

	public static void main(String[] args) {
		SpringApplication.run(BookshopApp.class, args);
	}
	
		
	@Component
	class MyCLRunner1 implements CommandLineRunner {

		@Override
		@Transactional
		public void run(String... args) throws Exception {
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<< ENTERED");
			Person person1 = new Person( "ABC-123" );
			Person person2 = new Person( "DEF-456" );

			Address address1 = new Address( "1st Avenue", "12A", "4005A" );
			Address address2 = new Address( "2nd Avenue", "18B", "4007B" );

			entityManager.persist( person1 );
			entityManager.persist( person2 );

			entityManager.persist( address1 );
			entityManager.persist( address2 );

			person1.addAddress( address1 );
			person1.addAddress( address2 );

			person2.addAddress( address1 );

			entityManager.flush();

			person1.removeAddress( address1 );
			
			///////////////////////////////////////
			
			Book book = Book.builder().isbn("99921-58-10-7").title("Signals").build();
			Author author = Author.builder().firstName("Darek").lastName("Ustrz").build();
			entityManager.persist( book );
			entityManager.persist( author );
			book.addAuthor(author);
			entityManager.flush();
			
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<< LEFT");
		}
	}

	
	
}

