
package pl.ust.bookshop.book;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.authorbook.AuthorBook;
import pl.ust.bookshop.model.BaseEntity;
import pl.ust.bookshop.publisher.Publisher;

@JsonIgnoreProperties(ignoreUnknown = true) 
@Getter @Setter @Builder 
@AllArgsConstructor  @NoArgsConstructor
@Where(clause = "is_deleted=false")
@EqualsAndHashCode(of = {"isbn"}, callSuper = false) 
@ToString(callSuper=true, includeFieldNames = false)
@Entity(name="books")

@Table( indexes = @Index(name = "idx_book_title", 
						columnList = "title", 
						unique = false)) // ,
public class Book extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Publisher publisher;
  
	@NaturalId
	//TODO @ISBN  messageTemplate='{org.hibernate.validator.constraints.ISBN.message}'
	@Check(constraints = "CASE WHEN isbn IS NOT NULL THEN LENGTH(isbn) = 13 ELSE true END")
	@Column(unique = true, nullable=false) 
	private String isbn;
	
  	private  String title;  
  
  	//@ColumnDefault("unassigned") //- not working when prepopulating: MySQLSyntaxErrorException: Table 'a_bookshop.books' doesn't exist
  	private  String type;
  
  	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup("lobs")
	private Blob coverImage;
  
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private Set<AuthorBook> authors;
	
	public void addAuthor(Author author) {
		AuthorBook authorBook = new AuthorBook(author, this);
		this.getAuthors().add(authorBook);
		author.getBooks().add(authorBook);

	}

    public void removeAuthor(Author author) {
        AuthorBook authorBook = new AuthorBook(author, this);
        author.getBooks().remove( authorBook );
        this.getAuthors().remove( authorBook );
        authorBook.setBook( null );
        authorBook.setAuthor( null );
    }

	/////////////// getters and setters ///////////////////

	public Set<AuthorBook> getAuthors() {
		if (this.authors == null) {
			this.authors = new HashSet<>();
		}
		return this.authors;
	}

  
}