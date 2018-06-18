package pl.ust.bookshop.authorbook;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false) @EqualsAndHashCode(callSuper=true)
@Where(clause = "is_deleted=false")
@Entity(name="authors_books")
public class AuthorBook extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Author author;
	
	@ManyToOne
	private Book book;

}
