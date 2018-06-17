package pl.ust.bookshop.author;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter
@AllArgsConstructor  @NoArgsConstructor
@Where(clause = "is_deleted=false")
@ToString(callSuper=true, includeFieldNames = false, exclude = "books")
@EqualsAndHashCode(callSuper=true)
@Entity(name="authors")
public class Author extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@ManyToMany
	private List<Book> books = new ArrayList<>();
	

}
