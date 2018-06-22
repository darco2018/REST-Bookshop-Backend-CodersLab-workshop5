package pl.ust.bookshop.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.publisher.Publisher;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private TestEntityManager tem;

	private Book book;

	@Before
	public void setUp() {
		new Book();
		book = Book.builder()
				.title("Alice in Wonderland")
				.isbn("99921-58-10-7")
				.build();
	}
	// TODO remove fully qualified names 
	
	@Test 
	public void shouldFindBookById() {

		// given
		Long id = (Long) tem.persistAndGetId(this.book);

		// when
		Book found = bookRepository.findById(id).get();

		// then
		assertThat(found.getId()).isNotNull();
		assertThat(found.getId()).isGreaterThan(0);
		assertThat(found.getTitle()).isEqualTo(this.book.getTitle());
	}

	@Test
	public void shouldFindBookByTitle() {
		// given
		tem.persistAndFlush(this.book);
		
		// when
		Book found = bookRepository.findByTitle(this.book.getTitle()).get();
		
		// then
		org.assertj.core.api.Assertions.assertThat(this.book.getTitle()).isEqualTo(found.getTitle());
	
	}
	
	@Test
	public void shouldCascadePersistToBook() {
		
		//given
		Publisher publisher = Publisher.builder().name("BookHouse").build();
		Long publisherId = tem.persistAndGetId(publisher, Long.class);
		
		//when
		publisher.addBook(this.book);
		tem.flush();
		
		//then
		Publisher foundPublisher = tem.find(Publisher.class, publisherId);
		assertThat(foundPublisher.getBooks()).isNotEmpty();
		
		Book foundBook = foundPublisher.getBooks().stream().findFirst().get();
		assertThat(foundBook.getTitle()).isEqualTo(this.book.getTitle());
		
		assertThat(foundBook.getPublisher().getId()).isEqualTo(publisherId);
	}
	
	@Test
	public void shouldCascadePersistToAuthor() throws InterruptedException {
		
		//given
		Long bookId = tem.persistAndGetId(this.book, Long.class);
		
		Author author = Author.builder().firstName("Lewis").lastName("Carol").build();
		tem.persist(author);
		
		//when
		this.book.addAuthor(author);
		/*
			tem.flush();  UNNECESSARY FLUSH throws exception StackOverflow
		
			Loading entity: [pl.ust.bookshop.book.Book#1]
		  	<< Author added
			Attempting to resolve: [pl.ust.bookshop.book.Book#1]
			<<<< Book found
			Resolved object in session cache: [pl.ust.bookshop.book.Book#1]
		*/
		
		
		//then
		Book foundBook = tem.find(Book.class, bookId);
		org.assertj.core.api.Assertions.assertThat(foundBook.getTitle()).isEqualTo(this.book.getTitle());
		
		Author foundAuthor = foundBook.getAuthors().stream().findFirst().get().getAuthor();
		org.assertj.core.api.Assertions.assertThat(foundAuthor.getLastName()).isEqualTo(author.getLastName());
	}
	
}
