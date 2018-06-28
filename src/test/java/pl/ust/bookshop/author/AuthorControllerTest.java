package pl.ust.bookshop.author;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashSet;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

	@MockBean
	private AuthorService authorService;

	@Autowired
	private MockMvc mockMvc;

	private Author mockAuthor;

	@Before
	public void setUp() {
		this.mockAuthor = Author.builder().firstName("Jessica").lastName("Thompson").email("jessica@gmail.com").build();
	}
	
	@Test
	public void shouldReturn404WhenFindWithWrongId() throws Exception {

		BDDMockito.given(this.authorService.findAuthorById(Mockito.eq(-1L)))
			.willThrow(new IllegalArgumentException());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/authors/" + Mockito.eq(-1L)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldAddAuthor() throws Exception {
		
		String authorAsJson = mapToJson(this.mockAuthor);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.post("/authors/add")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(authorAsJson);
		
		given(this.authorService.saveAuthor(Mockito.any(Author.class)))
				.willReturn(this.mockAuthor);
		
		mockMvc.perform(reqBuilder)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(content().json(authorAsJson))
			.andExpect(header().string("location", 
					org.hamcrest.Matchers.is("http://localhost/authors/0")));
		
		
	}
	

	@Test
	public void shouldFindAuthorById() throws Exception {

		when(this.authorService.findAuthorById(ArgumentMatchers.anyLong()))
				.thenReturn(this.mockAuthor);

		String authorAsJson = mapToJson(this.mockAuthor);

		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/authors/5")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status()
				.isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json(authorAsJson));

		Mockito.verify(this.authorService, Mockito.times(1)).findAuthorById(ArgumentMatchers.anyLong());
	}
	
	@Test
	public void shouldFindAllAuthors() throws Exception {
		
		Set<Author> authors = new LinkedHashSet<>();
		authors.add(this.mockAuthor);
		authors.add(Author.builder().firstName("Brian").lastName("Second").email("second@gmail.com").build());
		String authorsInJson = mapToJson(authors);
		
		given(this.authorService.findAllAuthors())
				.willReturn(authors);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/authors/")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		mockMvc.perform(reqBuilder)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(content().json(authorsInJson));
		
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
