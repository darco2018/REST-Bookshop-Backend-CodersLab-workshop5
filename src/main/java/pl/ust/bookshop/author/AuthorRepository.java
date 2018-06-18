package pl.ust.bookshop.author;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.ust.bookshop.publisher.Publisher;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
