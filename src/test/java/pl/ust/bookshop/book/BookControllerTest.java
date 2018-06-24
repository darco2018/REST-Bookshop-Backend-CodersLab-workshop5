package pl.ust.bookshop.book;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest (BookController.class)
public class BookControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	private Book book;
	
	@Before
	public void setUp() {
		this.book = new Book.BookBuilder().isbn("99921-58-10-7").title("Angel Eyes").build();
	}
	
	
	
	
	//@Test //TODO
	@Test
	public void shouldFindBook() throws Exception {
		
		//given( this.bookService.findById(ArgumentMatchers.anyLong())).willReturn(new Book("Angel Eyes", "99921-58-10-7"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("title").value("Angel Eyes"))
		.andExpect(jsonPath("isbn").value("99921-58-10-7"));
		
		
		/*mockMvc.perform(post("/books/add")
				.requestAttr("book", this.book)  )
		.param("isbn","99921-58-10-7")
		.param("title", "Angel Eyes")
		.param("id", "1"))
		.andDo(print())
		
		.andExpect(status().isOk());*/
	}
	
	//@Test //TODO
	public void shouldNotFindBook() throws Exception {
	
		given( this.bookService.findBookById(ArgumentMatchers.anyLong())).willThrow(new IllegalArgumentException());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
		.andExpect(status().isNotFound());
	}	
}
