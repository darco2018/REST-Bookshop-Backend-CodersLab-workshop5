package pl.ust.bookshop.publisher;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
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
	
	@Test
	public void shouldFindAllPublishers() throws Exception{
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/publishers/")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		Set<Publisher> publishers = new HashSet<>();
		publishers.add(this.mockPublisher);
		publishers.add(Publisher.builder().name("Second publisher").build());
		String expectedOutput = this.mapToJson(publishers);
		
		BDDMockito.when(this.publisherService.findAllPublishers())
				.thenReturn(publishers);
		
		mockMvc.perform(reqBuilder)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(expectedOutput));
		
	}
	
	
	@Test
	public void shouldAddPublisher() throws Exception {
		
		String publisherInJson = this.mapToJson(mockPublisher);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.post("/publishers/add")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(publisherInJson);
		
		BDDMockito.when(this.publisherService.savePublisher(this.mockPublisher))
			.thenReturn(this.mockPublisher);
		
		mockMvc.perform(reqBuilder)
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(content().json(publisherInJson))
		.andExpect(header().string("location", Matchers.is("http://localhost/publishers/0")));
	}
	
	@Test
	public void shouldReturn404WhenFindWithWrongId() throws Exception {
		
		long wrongId = -1L;
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.get("/publisher/" + wrongId);
				
		BDDMockito.when(this.publisherService.findPublisherById(wrongId))
			.thenThrow(new IllegalArgumentException());
		
		mockMvc.perform(reqBuilder)
		.andDo(print())
		.andExpect(status().isNotFound());
				
		
	}
	
	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
	}

}
