package pl.ust.bookshop.book;

import java.util.List;

public interface BookService {
	     
	    void saveBook(Book book);
	    void deleteBookById(long id);
	    Book findById(long id);
	    List<Book> findAllBooks(); 
	    boolean isBookExist(Book book);
	    
	 /*
	    Book findByName(String book);
	  
	   	void updateBook(Book book);
	     
	    void deleteAllBooks();
	     
	    
	     */
	}
