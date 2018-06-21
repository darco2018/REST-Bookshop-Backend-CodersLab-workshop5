package pl.ust.bookshop.book;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

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
		
		//when
		publisher.addBook(this.book);
		Long publisherId = tem.persistAndGetId(publisher, Long.class);
		tem.flush();
		
		//then
		Publisher found = tem.find(Publisher.class, publisherId);
		org.assertj.core.api.Assertions.assertThat(found.getBooks()).isNotEmpty();
		
		assertThat(found.getBooks().stream().findFirst().get().getTitle()).isEqualTo(this.book.getTitle());
		assertThat(found.getBooks().stream().findFirst().get().getId()).isEqualTo(publisherId);
		
	}

}
