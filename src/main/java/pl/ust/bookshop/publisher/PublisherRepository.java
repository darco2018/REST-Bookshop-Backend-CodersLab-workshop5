package pl.ust.bookshop.publisher;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long>{

	Optional<Publisher> findByName(String name);
	Set<Publisher> findAll();

}
