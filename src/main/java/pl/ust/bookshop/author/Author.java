package pl.ust.bookshop.author;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.authorbook.AuthorBook;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter @Builder 
@AllArgsConstructor  @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude = "books")
@EqualsAndHashCode(of = {"firstName", "lastName", "email"}, callSuper = true) 
@Where(clause = "is_deleted=false")
@Entity(name="authors")
@Table( indexes =  @Index( name = "idx_author_first_last_name",
	        columnList = "first_name, last_name", unique = false
	    )
	)
public class Author extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotBlank
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Email
	@NaturalId
	@Column(unique = true, nullable=true) 
	private String email;
	
	@JsonIgnore // prevents recursion when calling books
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private List<AuthorBook> books;

	/////////////// getters and setters ///////////////////

	public List<AuthorBook> getBooks() {
		if (this.books == null) {
			this.books = new ArrayList<>();
		}
		return this.books;
	}
	
	

}
