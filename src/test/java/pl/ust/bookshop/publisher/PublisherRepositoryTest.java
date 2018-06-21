package pl.ust.bookshop.publisher;

import static org.junit.Assert.fail;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PublisherRepositoryTest {
	
	@Autowired
	private TestEntityManager tem;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	private Publisher publisher;

	@Before
	public void setUp() {
		this.publisher = Publisher.builder().name("GoodBooks co.").build();
	}
	
	@Test
	public void shouldFindPublisherById() {
		
		//given
		Object id = tem.persistAndGetId(this.publisher);
		
		//when
		Publisher found = tem.find(Publisher.class, id);
		
		//then
		
		org.assertj.core.api.Assertions.assertThat(found.getId()).isNotNull();
		Assertions.assertThat(found.getId()).isGreaterThan(0);
		Assertions.assertThat(found.getName()).isEqualTo(this.publisher.getName());
		
	}
	
	@Test
	public void shouldFindPublisherByName() {
		
		//given
		tem.persistAndFlush(this.publisher);
		
		//when
		Publisher found = publisherRepository.findByName(this.publisher.getName()).get();
		
		//then
		org.assertj.core.api.Assertions.assertThat(found.getName()).isEqualTo(this.publisher.getName());
		
		
	}
	
	
	

}
