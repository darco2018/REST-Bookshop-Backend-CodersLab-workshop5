
package pl.ust.bookshop.book;

import java.net.URI;
import java.util.Set;

import javax.validation.constraints.NotNull;

//import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@CrossOrigin  //TODO
@RestController 
@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@RequestMapping("/books")
public class BookController {
	
	//@Autowired
	private final @NotNull BookService bookService;   
	/* lombok REPLACES this:
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}*/
	
	@RequestMapping(value="/", method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Set<Book>> listBooks() {
        Set<Book> books = bookService.findAllBooks();
        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(books, HttpStatus.OK); 
    }


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE) 
	public ResponseEntity<Book> viewBook(@PathVariable("id") long id) {
		Book book = bookService.findBookById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<>(book, HttpStatus.OK); 
		
		//return RestPreconditions.checkFound( service.findOne( id ));
	}
	
	// @ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/add", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Book> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
		
		//Preconditions.checkNotNull(book);
		
		if (book == null)
			return ResponseEntity.noContent().build();
		
		if (bookService.isBookExist(book)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); 
		}

		Book saved = bookService.saveBook(book);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri());
		
		/*
		 ALTERNATYWNIE
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
		return ResponseEntity.created(location).build(); // Create a new builder with a CREATED status and a location header set to the given URI.
		*/
		return new ResponseEntity<>(saved, headers, HttpStatus.CREATED); 
	}                                                                
	

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {

		Book book = bookService.findBookById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); // OK ?
	}
	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	   @ResponseStatus(HttpStatus.OK)
	   public void update(@PathVariable( "id" ) Long id, @RequestBody Book book) {
	       Preconditions.checkNotNull(book);
	       RestPreconditions.checkNotNull(bookService.getById( book.getId()));
	       bookService.update(book);
	   }
	   
	   You may have noticed I’m using a straightforward, Guava style RestPreconditions utility:

public class RestPreconditions {
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException();
        }
        return resource;
    }
}
	   */
	
	/*
	@RequestMapping(method = RequestMethod.GET)
	   @ResponseBody
	   public List<Book> findAll() {
	       return bookService.findAll();
	   }
	*/
	
	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void bookNotFound(IllegalArgumentException ex) {
		//TODO
	}
	/*
	n the case of a client error, custom exceptions are defined and mapped to the appropriate error codes.

	Simply throwing these exceptions from any of the layers of the web tier will ensure Spring maps the corresponding 
	status code on the HTTP response.

	These exceptions are part of the REST API and, as such, should only be used in the appropriate layers
	 corresponding to REST; if for instance, a DAO/DAL layer exists, it should not use the exceptions directly.
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class BadRequestException extends RuntimeException {
	   //
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {
	   //
	}*/
	
	

}
