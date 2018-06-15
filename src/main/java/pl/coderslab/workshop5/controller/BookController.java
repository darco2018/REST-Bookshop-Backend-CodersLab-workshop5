
package pl.coderslab.workshop5.controller;
/*
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;*/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.coderslab.workshop5.model.Book;
import pl.coderslab.workshop5.service.BookService;

// @ResponseBody As of version 4.0 this annotation can also be added on the type level
// in which case it is inherited and does not need to be added on the method
// level. this RESTful web service controller simply populates and returns a Book object. 
//The object data will be written directly to the HTTP response as JSON.

// This code uses Spring 4’s new @RestController annotation, which marks the class as 
// a controller where every method returns a domain object instead of a view. 
/*
The Book object must be converted to JSON. Thanks to Spring’s HTTP message converter support, 
you don’t need to do this conversion manually. Because Jackson 2 is on the classpath, 
Spring’s MappingJackson2HttpMessageConverter is automatically chosen to convert 
the Book instance to JSON.
*/
@RestController 
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;   //alternatywa: private final CopyOnWriteArrayList<Book> cList = MockBookList.getInstance(); 

	
	//-------------------Test --------------------------------------------------------

	@RequestMapping("/hello")
	public String hello() {
		return "{hello:	World}";
	}

	@RequestMapping("/helloBook")
	public Book helloBook() {
		return new Book(1L, "9788324631766", "Thinking	in	Java", "Bruce	Eckel", "Helion", "programming");
	}
	
	/*@RequestMapping("/") // "" mapuje tez do "/"
	public List<Book> listAllBooks() {
		System.out.println("1. listAllBooks");
		return  bookService.findAllBooks();
	}*/
	
	//-------------------Retrieve All --------------------------------------------------------
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listAllBooks2() {
        List<Book> books = bookService.findAllBooks();
        if(books.isEmpty()){
            return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);//or HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK); //instead of: return  bookService.findAllBooks();
    }

	
	// -------------------Retrieve one------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // , 
	public ResponseEntity<Book> getBook(@PathVariable("id") long id) {
		Book book = bookService.findById(id);
		if (book == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND); // HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	

	// -------------------Create one-------------------------------------------

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// type dla REsponseEntity void!
	public ResponseEntity<Void> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
								// @RequestBody 
		if (bookService.isBookExist(book)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT); // HttpStatus.CONFLICT
		}

		bookService.saveBook(book);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED); // headers set for response
	}                                                                 // HttpStatus.CREATED
	
	// -------------------Delete one-------------------------------------------

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {

		Book book = bookService.findById(id);
		if (book == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}

		bookService.deleteBookById(id);
		return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
	}

}
