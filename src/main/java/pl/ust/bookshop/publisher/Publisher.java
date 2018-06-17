package pl.ust.bookshop.publisher;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter @Builder
@AllArgsConstructor  @NoArgsConstructor
@Where(clause = "is_deleted=false")
@ToString(callSuper=true, includeFieldNames = false, exclude = "books")
@EqualsAndHashCode(callSuper=true)
@Entity(name="publishers")
public class Publisher extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty 
	@Column
	private String name;
	
	@OneToMany(mappedBy = "publisher", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@Singular private List<Book> books;

	/////////////// getters and setters ///////////////////

	public List<Book> getBooks() {
		if (this.books == null) {
			this.books = new ArrayList<>();
		}
		return this.books;
	}

	/////////////// helpers for Lesson ///////////////////

	public void addBook(Book book) {
		this.books.add(book);
		book.setPublisher(this);
	}

	public void removeBook(Book book) {
		book.setPublisher(null);
		this.books.remove(book);
	}

	public void removeAllLessons() {

		for (Book book : this.getBooks()) {
			book.setPublisher(null);
		}
		this.books.clear();
	}

}
