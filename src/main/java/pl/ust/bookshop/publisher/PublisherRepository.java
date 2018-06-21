package pl.ust.bookshop.publisher;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

	Optional<Publisher> findByName(String name);

}
