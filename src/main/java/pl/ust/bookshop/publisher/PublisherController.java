package pl.ust.bookshop.publisher;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.ust.bookshop.book.Book;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

	private PublisherService publisherService;

	public PublisherController(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@GetMapping("")
	public ResponseEntity<Set<Publisher>> listPublishers() {
		Set<Publisher> publishers = this.publisherService.findAllPublishers();
		if (publishers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(publishers, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<Publisher> viewPublisher(@PathVariable long id) {
		Publisher publisher = this.publisherService.findPublisherById(id);

		if (publisher == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(publisher, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createPublisher(@RequestBody Publisher publisher, UriComponentsBuilder ucBuilder) {

		if (this.publisherService.isPublisherExist(publisher)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		this.publisherService.savePublisher(publisher);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/publishers/{id}").buildAndExpand(publisher.getId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Book> deletePublisher(@PathVariable("id") long id) {

		Publisher publisher = this.publisherService.findPublisherById(id);
		if (publisher == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		this.publisherService.deletePublisherById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
