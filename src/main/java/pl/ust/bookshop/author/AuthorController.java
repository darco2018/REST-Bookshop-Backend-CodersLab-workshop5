package pl.ust.bookshop.author;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import pl.ust.bookshop.book.Book;

@RestController
@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@RequestMapping("/authors")
public class AuthorController {
	
	private final @NotNull AuthorService authorService;
	
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Author> createAuthor(@RequestBody Author author, UriComponentsBuilder ucBuilder){
		
		if (author == null) {
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.build();
		}
		
		if (this.authorService.isAuthorExist(author)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); 
		}
		
		Author saved = this.authorService.saveAuthor(author);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder
				.path("/authors/{id}")
				.buildAndExpand(author.getId())
				.toUri());
		
		return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Author> viewAuthor(@PathVariable long authorId){
		
		Author found = this.authorService.findAuthorById(authorId);
		
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(found, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Set<Author>> listAuthors(){
		
		Set<Author> authors = this.authorService.findAllAuthors();
		
		if(authors.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(authors, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {

		Author book = this.authorService.findAuthorById(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		this.authorService.deleteAuthorById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	
	

}
