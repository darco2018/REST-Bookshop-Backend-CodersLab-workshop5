package pl.ust.bookshop.book;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@Service
public class BookServiceImpl implements BookService{
	
	private final @NonNull  BookRepository bookRepository;
	
	@Override
	public Book saveBook(Book book) {

		return this.bookRepository.save(book);
	}
	
	
	@Override
	public void deleteBookById(long id) {
		this.bookRepository.deleteById(id);
	}
	
	@Override
	public Book findBookById(long id) {
		Optional<Book> opt = this.bookRepository.findById(id);
		return opt.orElseThrow(IllegalArgumentException::new);
	}
	
	@Override
	public Set<Book> findAllBooks() {
		return this.bookRepository.findAll();
	}


	@Override
	public boolean isBookExist(Book book) {
		return this.bookRepository.existsById(book.getId());
	}

	

}
