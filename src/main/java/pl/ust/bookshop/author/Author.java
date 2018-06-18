package pl.ust.bookshop.author;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.book.Book;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter @AllArgsConstructor  @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false, exclude = "books")
@EqualsAndHashCode(callSuper=true)
@Where(clause = "is_deleted=false")
@Entity(name="authors")
@Table( indexes =  @Index( name = "idx_author_first_last_name",
	        columnList = "first_name, last_name", unique = false
	    )
	)
public class Author extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NaturalId(mutable = true)
	private String email;
/*
	
	@ManyToMany(mappedBy = "authors")
	private List<Book> books = new ArrayList<>();
	*/
	
	

}
