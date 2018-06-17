
package pl.ust.bookshop.book;

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


@RestController 
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;   
	
	
	//-------------------Retrieve All --------------------------------------------------------
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listAllBooks2() {
        List<Book> books = bookService.findAllBooks();
        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//or HttpStatus.NOT_FOUND
        }
        
        return new ResponseEntity<>(books, HttpStatus.OK); //instead of: return  bookService.findAllBooks();
    }

	
	// -------------------Retrieve one------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) // , 
	public ResponseEntity<Book> getBook(@PathVariable("id") long id) {
		Book book = bookService.findById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	

	// -------------------Create one-------------------------------------------

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// type dla REsponseEntity void!
	public ResponseEntity<Void> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
								
		if (bookService.isBookExist(book)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); 
		}

		bookService.saveBook(book);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED); 
	}                                                                
	
	// -------------------Delete one-------------------------------------------

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {

		Book book = bookService.findById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
