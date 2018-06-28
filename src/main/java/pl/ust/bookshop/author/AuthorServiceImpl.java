package pl.ust.bookshop.author;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AuthorServiceImpl implements AuthorService{
	
	private final @NonNull AuthorRepository authorRepo;

	@Override
	public Author saveAuthor(Author author) {
		Assert.notNull(author, "The author can't be null!");
		return this.authorRepo.save(author);
	}

	@Override
	public void deleteAuthorById(long authorId) {
		this.authorRepo.deleteById(authorId);
	}

	@Override
	public Author findAuthorById(long id) {
		Optional<Author> opt = this.authorRepo.findById(id);
		return opt.orElseThrow(IllegalArgumentException::new);
	}

	@Override
	public Set<Author> findAllAuthors() {
		return this.authorRepo.findAll();
	}
	
	@Override
	public boolean isAuthorExist(Author author) {
		Assert.notNull(author, "The author can't be null!");
		return this.authorRepo.existsById(author.getId());
	}

	

}
