package pl.ust.bookshop.publisher;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	private String name;
	
	@JsonIgnore // prevents recursion when calling books
	@OneToMany(mappedBy = "publisher", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	
	private Set<Book> books; // = new ArrayList<>();

	/////////////// getters and setters ///////////////////

	public Set<Book> getBooks() {
		if (this.books == null) {
			this.books = new HashSet<>();
		}
		return this.books;
	}

	/////////////// helpers ///////////////////

	public void addBook(Book book) {
		this.getBooks().add(book);
		book.setPublisher(this);
	}

	public void removeBook(Book book) {
		book.setPublisher(null);
		this.getBooks().remove(book);
	}

	public void removeAllBooks() {

		for (Book book : this.getBooks()) {
			book.setPublisher(null);
		}
		this.books.clear();
	}

}
