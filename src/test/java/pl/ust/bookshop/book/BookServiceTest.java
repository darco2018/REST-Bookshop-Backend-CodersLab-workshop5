package pl.ust.bookshop.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
	
	@Mock
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	private Book book;
	
	@Before
	public void setUp() throws Exception {
		
		bookService = new BookServiceImpl(bookRepository); // cant initialize it once 
		
		book = Book.builder().title("Alice in Wonderland").isbn("99921-58-10-1").build();
		
	}
                                               // TODO remove fully qualified names 
	@Test
	public void shouldFindAllBooks() {
		
		//given	
		bookService = new BookServiceImpl(bookRepository);
		
		org.mockito.BDDMockito.given(this.bookRepository.findAll())
			.willReturn( org.assertj.core.util.Sets.newLinkedHashSet(this.book, 
					Book.builder().title("Second book").isbn("99921-58-10-2").build()));
		
		//when
		Set<Book> books = bookService.findAllBooks();
		http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2568658
		//then
		org.assertj.core.api.Assertions.assertThat(books.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindBook() {
		
		//given		
		org.mockito.BDDMockito.given( this.bookRepository.existsById(ArgumentMatchers.anyLong()) )
								.willReturn(true);
		
		//then
		boolean match = bookService.isBookExist(this.book);
		
		//when
		org.assertj.core.api.Assertions.assertThat(match).isTrue();

	}
	
	@Test
	public void shouldThrowException_whenBookNotFound() {
		
		//given	
		org.mockito.BDDMockito.given( this.bookRepository.findById(- anyLong()))
			.willReturn(Optional.empty());
		
		//when
		Throwable thrown = catchThrowable(() -> {
			bookService.findBookById(-1L);
		});
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

}
