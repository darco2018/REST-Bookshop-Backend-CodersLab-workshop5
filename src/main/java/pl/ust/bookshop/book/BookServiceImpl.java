package pl.ust.bookshop.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BookServiceImpl implements BookService{
	
	private final @NonNull  BookRepository bookRepository;
	
	/*
	  NOTE: use final and @NonNull.
	  lombok replaces:
	  
	  public BookServiceImpl(BookRepository publisherRepo) {
	    Assert.notNull(collaborator, "BookRepository must not be null!");
		this.publisherRepo = publisherRepo;
	}*/
	
	@Override
	public void saveBook(Book book) {
		this.bookRepository.save(book);
	}
	
	
	@Override
	public void deleteBookById(long id) {
		this.bookRepository.deleteById(id);
	}
	
	@Override
	public Book findById(long id) {
		Optional<Book> opt = this.bookRepository.findById(id);
		return opt.orElseThrow(IllegalArgumentException::new);
	}
	
	@Override
	public List<Book> findAllBooks() {
		return this.bookRepository.findAll();
	}


	@Override
	public boolean isBookExist(Book book) {
		return this.bookRepository.existsById(book.getId());
	}

	

}
