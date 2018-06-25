package pl.ust.bookshop.author;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.author.AuthorRepository;
import pl.ust.bookshop.author.AuthorService;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AuthorServiceImpl implements AuthorService{
	
	private final @NonNull AuthorRepository authorRepo;

	@Override
	public Author saveAuthor(Author author) {
		return this.authorRepo.save(author);
	}

	@Override
	public void deleteAuthorById(long authorId) {
		this.authorRepo.deleteById(authorId);
	}

	@Override
	public Author findById(long id) {
		Optional<Author> opt = this.authorRepo.findById(id);
		return opt.orElseThrow(IllegalArgumentException::new);
	}

	@Override
	public List<Author> findAllAuthors() {
		return this.authorRepo.findAll();
	}
	
	@Override
	public boolean isAuthorExist(Author author) {
		return this.authorRepo.existsById(author.getId());
	}

	

}
