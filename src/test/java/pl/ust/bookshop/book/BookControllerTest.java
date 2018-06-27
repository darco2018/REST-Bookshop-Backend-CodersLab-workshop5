package pl.ust.bookshop.book;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest (BookController.class) 
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc; 	
	
	@MockBean 
	private BookService bookService;
	
	private Book mockBook;
	
	@Before
	public void setUp()  {
		
		this.mockBook = new Book.BookBuilder()
						.isbn("99921-58-10-7")
						.title("Angel Eyes")
						.build();
	}
	
	@Test 
	public void shouldReturnAllBooks() throws Exception {
		
		Set<Book> books = new HashSet<>();
		books.add(this.mockBook);
		books.add(Book.builder().isbn("99921-58-10-1").title("The Kings").build());
		
		Mockito.when(this.bookService.findAllBooks())
			.thenReturn(books);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/books/")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		String expectedJson = mapToJson(books);
		
		mockMvc.perform(reqBuilder)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
	
	@Test 
	public void shouldFindBookById() throws Exception {
		
		Mockito.when( this.bookService.findBookById(ArgumentMatchers.anyLong()))
			.thenReturn(this.mockBook);
		
		String expectedJson = mapToJson(this.mockBook);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/books/1")
				.accept(MediaType.APPLICATION_JSON_UTF8);
		
		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect( MockMvcResultMatchers.status().isOk()) 
				.andExpect( MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect( MockMvcResultMatchers.content().json(expectedJson))
				.andExpect( MockMvcResultMatchers.jsonPath("title").value(this.mockBook.getTitle()))
				.andExpect( MockMvcResultMatchers.jsonPath("$.isbn", org.hamcrest.Matchers.is(this.mockBook.getIsbn())));
		
		Mockito.verify(this.bookService, Mockito.times(1)).findBookById(ArgumentMatchers.anyLong()); 
	}

	
	
	@Test
	public void shouldAddBook() throws Exception {
		
		Mockito.when( this.bookService.saveBook(Mockito.any(Book.class)))
				.thenReturn(this.mockBook);
		
		String URI = "/books/add";
		String expectedJson = mapToJson(this.mockBook);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(expectedJson);
		
		mockMvc.perform(reqBuilder)
				.andExpect( MockMvcResultMatchers.status().isCreated())
				.andExpect( MockMvcResultMatchers.content().json(expectedJson))
				.andExpect( MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect( MockMvcResultMatchers.jsonPath("title").value("Angel Eyes"))
				.andExpect( MockMvcResultMatchers.jsonPath("$.isbn", org.hamcrest.Matchers.is("99921-58-10-7")))
				.andExpect( MockMvcResultMatchers.jsonPath("isbn").value("99921-58-10-7"))
				.andExpect(MockMvcResultMatchers.header().string("location", 
						org.hamcrest.Matchers.is("http://localhost/books/0")));
		}

	@Test
	public void shouldReturn404WhenFindWithWrongId() throws Exception {

		BDDMockito.given(this.bookService.findBookById(Mockito.eq(-1L)))
			.willThrow(new IllegalArgumentException());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/books/" + Mockito.eq(-1L)))
				.andExpect(status().isNotFound());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
