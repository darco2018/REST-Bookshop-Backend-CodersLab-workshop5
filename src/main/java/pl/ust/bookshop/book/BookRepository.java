package pl.ust.bookshop.book;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
	
	Optional<Book> findByTitle(String title);
	Set<Book> findAll();
	

}
