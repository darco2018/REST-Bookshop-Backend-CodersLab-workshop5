package pl.ust.bookshop.publisher;

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
@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
	
	@MockBean
	private PublisherService publisherService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Publisher mockPublisher;
	
	@Before
	public void setUp() {
		 
		mockPublisher = Publisher.builder().name("BookHouse Co.").build();
	}

	@Test
	public void shouldFindPublisherById() throws Exception {
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/publishers/1")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		when(this.publisherService.findPublisherById(ArgumentMatchers.anyLong()))
				.thenReturn(this.mockPublisher);
		
		String expectedOutput = mapToJson(this.mockPublisher);
		
		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json(expectedOutput));
		
		Mockito.verify(this.publisherService, Mockito.times(1)).findPublisherById(ArgumentMatchers.anyLong());
				
	}
	
	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
	}

}
