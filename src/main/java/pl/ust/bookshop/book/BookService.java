package pl.ust.bookshop.book;

import java.util.List;

public interface BookService {
	     
		Book findById(long id);
	     
		Book findByName(String book);
	     
	    void saveBook(Book book);
	     
	    void updateBook(Book book);
	     
	    void deleteBookById(long id);
	 
	    List<Book> findAllBooks(); 
	     
	    void deleteAllBooks();
	     
	    boolean isBookExist(Book book);
	     
	}
