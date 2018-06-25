package pl.ust.bookshop.author;

import java.util.List;


public interface AuthorService {
	
	Author saveAuthor(Author author);
    void deleteAuthorById(long id);
    Author findById(long id);
    List<Author> findAllAuthors(); 
    boolean isAuthorExist(Author author);
    
 /*
    Author findByName(String author);
  
   	void updateAuthor(Author author);
     
    void deleteAllAuthors();
     
    
     */

}
