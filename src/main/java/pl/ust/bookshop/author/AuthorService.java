package pl.ust.bookshop.author;

import java.util.Set;


public interface AuthorService {
	
	Author saveAuthor(Author author);
    void deleteAuthorById(long id);
    Author findAuthorById(long id);
    Set<Author> findAllAuthors(); 
    boolean isAuthorExist(Author author);
    
 /*
    Author findByName(String author);
  
   	void updateAuthor(Author author);
     
    void deleteAllAuthors();
     
    
     */

}
