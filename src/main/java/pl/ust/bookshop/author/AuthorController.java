package pl.ust.bookshop.author;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.ust.bookshop.book.Book;

@RestController
@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@RequestMapping("/authors")
public class AuthorController {
	
	private final @NotNull AuthorService authorService;
	
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
	
	
	

}
