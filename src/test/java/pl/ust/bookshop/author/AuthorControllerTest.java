package pl.ust.bookshop.author;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
	public void shouldFindAuthorById() throws Exception {

		when(this.authorService.findAuthorById(ArgumentMatchers.anyLong()))
				.thenReturn(this.mockAuthor);

		String authorAsJson = mapToJson(this.mockAuthor);

		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/authors/5")
				.accept(MediaType.APPLICATION_JSON_UTF8);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status()
				.isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(authorAsJson));

		Mockito.verify(this.authorService, Mockito.times(1)).findAuthorById(ArgumentMatchers.anyLong());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
