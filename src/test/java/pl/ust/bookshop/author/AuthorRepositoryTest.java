package pl.ust.bookshop.author;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	TestEntityManager tem;
	
	private Author author;
	
	@Before
	public void setUp() {
		author = Author.builder().firstName("John").lastName("Milton").email("milton@gmail.com").build();
	}
	
	@Test
	public void shouldFindAuthorByFirstAndLastName() {
		
		//given
		tem.persistFlushFind(this.author);
		
		//when
		Author found = authorRepository.findByFirstNameAndLastName(this.author.getFirstName(),
																	this.author.getLastName()).get();
		
		//then
		org.assertj.core.api.Assertions.assertThat(found.getFirstName()).isEqualTo(this.author.getFirstName());
		
		
	}

}
