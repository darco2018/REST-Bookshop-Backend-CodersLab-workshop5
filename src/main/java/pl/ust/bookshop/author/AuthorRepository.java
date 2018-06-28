package pl.ust.bookshop.author;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
	Set<Author> findAll();
}
