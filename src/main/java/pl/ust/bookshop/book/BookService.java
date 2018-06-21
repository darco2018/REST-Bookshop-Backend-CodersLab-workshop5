package pl.ust.bookshop.book;

import java.util.List;
import java.util.Set;

public interface BookService {
	     
	    void saveBook(Book book);
	    void deleteBookById(long id);
	    Book findBookById(long id);
	    Set<Book> findAllBooks(); 
	    boolean isBookExist(Book book);
	    
	 /*
	    Book findByName(String book);
	  
	   	void updateBook(Book book);
	     
	    void deleteAllBooks();
	     
	    
	     */
	}
