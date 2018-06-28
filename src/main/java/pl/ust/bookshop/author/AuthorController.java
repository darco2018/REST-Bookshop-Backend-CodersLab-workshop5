package pl.ust.bookshop.author;

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

@RestController
@RequiredArgsConstructor(onConstructor=@__({@Autowired}))
@RequestMapping("/authors")
public class AuthorController {
	
	private final @NotNull AuthorService authorService;
	
	@GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	private ResponseEntity<Author> viewAuthor(@PathVariable long authorId){
		
		Author found = this.authorService.findAuthorById(authorId);
		
		if(found == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(found, HttpStatus.OK);
	}
	
	
	

}
