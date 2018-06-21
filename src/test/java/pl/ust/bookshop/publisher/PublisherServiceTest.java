package pl.ust.bookshop.publisher;

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
public class PublisherServiceTest {

	@Mock
	private PublisherRepository publisherRepository;
	
	@Autowired
	private PublisherService publisherService;
	
	private Publisher publisher;
	
	@Before
	public void setUp() throws Exception {
		
		publisherService = new PublisherServiceImpl(publisherRepository); // cant initialize it once 
		
		publisher = Publisher.builder().name("House of Books").build();
		
	}
                                               // TODO remove fully qualified names 
	@Test
	public void shouldFindAllPublishers() {
		
		//given	
		publisherService = new PublisherServiceImpl(publisherRepository);
		
		org.mockito.BDDMockito.given(this.publisherRepository.findAll())
			.willReturn( org.assertj.core.util.Sets.newLinkedHashSet(this.publisher, 
					Publisher.builder().name("Second publisher").build()));
		
		//when
		Set<Publisher> publishers = publisherService.findAllPublishers();
		
		//then
		org.assertj.core.api.Assertions.assertThat(publishers.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindPublisher() {
		
		//given		
		org.mockito.BDDMockito.given( this.publisherRepository.existsById(ArgumentMatchers.anyLong()) )
								.willReturn(true);
		
		//then
		boolean match = publisherService.isPublisherExist(this.publisher);
		
		//when
		org.assertj.core.api.Assertions.assertThat(match).isTrue();

	}
	
	@Test
	public void shouldThrowException_whenPublisherNotFound() {
		
		//given	
		org.mockito.BDDMockito.given( this.publisherRepository.findById(- anyLong()))
			.willReturn(Optional.empty());
		
		//when
		Throwable thrown = catchThrowable(() -> {
			publisherService.findPublisherById(-1L);
		});
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

}
