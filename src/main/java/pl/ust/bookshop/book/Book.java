
package pl.ust.bookshop.book;

import java.sql.Blob;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.model.BaseEntity;
import pl.ust.bookshop.publisher.Publisher;

@JsonIgnoreProperties(ignoreUnknown = true) 
@Getter @Setter @Builder @AllArgsConstructor  @NoArgsConstructor
@Where(clause = "is_deleted=false")
@EqualsAndHashCode(of = {"isbn"}, callSuper = true) @ToString(callSuper=true, includeFieldNames = false)
@Entity(name="books")

@Table( indexes = @Index(name = "idx_book_title", 
						columnList = "title", 
						unique = false)) // ,
		//uniqueConstraints =  @UniqueConstraint( name = "uc_title_authorid", 
		//										columnNames = { "title", "author_id" }))
public class Book extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	// @JsonIgnore
	@ManyToOne
	private Publisher publisher;
  
	@NaturalId
	@Check(constraints = "CASE WHEN isbn IS NOT NULL THEN LENGTH(isbn) = 13 ELSE true END")
	@Column(unique = true)
	private String isbn;
  
  	private  String title;  
  
  	//@ColumnDefault("unassigned") //- not working when prepopulating: MySQLSyntaxErrorException: Table 'a_bookshop.books' doesn't exist
  	private  String type;
  
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup("lobs")
	private Blob coverImage;
  /*
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors;

	public void addAuthor(Author author) {
		this.authors.add(author);
		author.getBooks().add(this);
	}

	public void removeAuthor(Author author) {
		this.authors.remove(author);
		author.getBooks().remove(this);
	}
  
  
  */
  
  
  
  
 
  
}