package pl.ust.bookshop;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.author.AuthorService;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.book.BookService;
import pl.ust.bookshop.publisher.Publisher;
import pl.ust.bookshop.publisher.PublisherService;

public class DBPopulator {
	
	private DBPopulator() {}

	private static final Logger log = LoggerFactory.getLogger(BookshopApp.class);

	static void populatePublishers(PublisherService publisherService) {
		log.info("{}", "---------------Populating the publishers-----------------------");

		String[] publisherNames = { "Printing House", "Agora", "Books Ltd.", "Zara Books" };
		Arrays.asList(publisherNames).stream().forEach(publisherName -> {
			Publisher publisher = Publisher.builder().name((String) publisherName).build();
			publisherService.savePublisher(publisher);
		});

		log.info("{}","---------------END Populating the publishers-----------------------");
	}

	static void populateAuthors(AuthorService authorService) {
		log.info("{}","---------------Populating the authors-----------------------");

		String[] authorFirstNames = { "John", "Mike", "Tom", "Lucas", "Julia", "Mary" };
		String[] authorLastNames = { "Brown", "Smith", "Aldridge", "Camel", "Donne", "Smiles", };
		String[] auhtorEmails = { "brown@gmail.com", "smith@gmail.com", "aldridge@gmail.com", "camel@gmail.com",
				"donne@gmail.com", "smiles@gmail.com" };

		for (int x = 0; x < authorFirstNames.length; x++) {
			Author author = Author.builder().firstName(authorFirstNames[x]).lastName(authorLastNames[x])
					.email(auhtorEmails[x]).build();
			authorService.saveAuthor(author);
		}

	}

	static void populateBooks(BookService bookService, PublisherService publisherService, AuthorService authorService) {

		String[] bookTitles = { "Angel Eyes", "Brown Beasts", "Creator of Love", "Dungeons and Diggers",
				"Early morning", "Faces of Doom", "Green Apples", "Horror Stories", "Icarus and Airplanes",
				"Jackyll or Hide", "Kangaroo's Tales", "Loving Memory" };

		String[] isbns = { "99921-58-10-7", "1-84356-028-3", "0-684-84328-5", "0-943396-04-2", "0-9752298-0-X",
				"0-684-84328-4", "99921-58-10-1", "1-84356-028-2", "0-684-84328-1", "0-943396-04-3", "0-9752297-0-X",
				"978-3-16-148410-0" };


		long[] publisherIds = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4 };
		long[] auhtorIds 	= { 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 8, 9 };

		for (int x = 0; x < bookTitles.length; x++) {

			Book book = Book.builder()
					.title(bookTitles[x])
					.isbn(isbns[x])
					.publisher(publisherService.findPublisherById(publisherIds[x])).build();
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<SQVING BOOK" );
			bookService.saveBook(book);
			/*
			Author author = authorService.findById(auhtorIds[x]);
			book.getAuthors().size();
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<" + book.getAuthors().size());
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<" + book.getPublisher(););
			
			book.addAuthor(author);
			bookService.saveBook(book);*/
		}

	}
	
}
