package pl.ust.bookshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.publisher.Publisher;

public class DBPopulator {

	private static final Logger LOG = LoggerFactory.getLogger(BookshopApp.class);
	
	private DBPopulator() {}

	static void populatePublishers(final EntityManager entityManager) {
		LOG.info("---------------Populating the publishers-----------------------");

		String[] publisherNames = { "Printing House", "Agora", "Books Ltd.", "Zara Books" };
		Arrays.asList(publisherNames).stream().forEach(publisherName -> {
			Publisher publisher = Publisher.builder().name((String) publisherName).build();
			entityManager.persist(publisher);
		});

		LOG.info("---------------END Populating the publishers-----------------------");
	}

	static void populateAuthors(EntityManager entityManager) {
		LOG.info("---------------Populating the authors-----------------------");

		final String[] authorFirstNames = { "John", "Mike", "Tom", "Lucas", "Julia", "Mary" };
		final String[] authorLastNames = { "Brown", "Smith", "Aldridge", "Camel", "Donne", "Smiles" };
		final String[] auhtorEmails = { "brown@gmail.com", "smith@gmail.com", "aldridge@gmail.com", "camel@gmail.com",
				"donne@gmail.com", "smiles@gmail.com" };

		for (int x = 0; x < authorFirstNames.length; x++) {
			Author author = Author.builder().firstName(authorFirstNames[x]).lastName(authorLastNames[x])
					.email(auhtorEmails[x]).build();

			entityManager.persist(author);
		}

	}

	static void populateBooks(EntityManager entityManager) {
		LOG.info("---------------Populating the books-----------------------");

		String[] bookTitles = { "Angel Eyes", "Brown Beasts", "Creator of Love", "Dungeons and Diggers",
				"Early morning", "Faces of Doom", "Green Apples", "Horror Stories", "Icarus and Airplanes",
				"Jackyll or Hide", "Kangaroo's Tales", "Loving Memory" };

		String[] isbns = { "99921-58-10-7", "1-84356-028-3", "0-684-84328-5", "0-943396-04-2", "0-9752298-0-X",
				"0-684-84328-4", "99921-58-10-1", "1-84356-028-2", "0-684-84328-1", "0-943396-04-3", "0-9752297-0-X",
				"978-3-16-148410-0" };

		long[] publisherIds = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4 };

		for (int x = 0; x < bookTitles.length; x++) {

			Book book = Book.builder().title(bookTitles[x]).isbn(isbns[x])
					.publisher(entityManager.find(Publisher.class, publisherIds[x])).build();
			entityManager.persist(book);

			Query q = entityManager.createNativeQuery("select * from authors", Author.class);
			List<Author> authors = new ArrayList<>();
			authors = q.getResultList();
			Assert.notEmpty(authors, "The list should contain Authors!");

			switch (x) {

			case 0:
			case 1:
			case 2:
			case 11:
				book.addAuthor(authors.get(1));
				break;
			case 3:
			case 4:
			case 5:
				book.addAuthor(authors.get(2));
				break;
			case 6:
			case 7:
				book.addAuthor(authors.get(3));
				break;
			case 8:
				book.addAuthor(authors.get(4));
				break;
			case 9:
			case 10:
				book.addAuthor(authors.get(5));
				break;
			}
			
			entityManager.flush();
		}

	}

}
