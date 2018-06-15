package pl.coderslab.workshop5.service;

import java.util.List;

import pl.coderslab.workshop5.model.Book;

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
