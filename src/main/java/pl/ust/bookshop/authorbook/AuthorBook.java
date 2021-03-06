package pl.ust.bookshop.authorbook;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.ust.bookshop.author.Author;
import pl.ust.bookshop.book.Book;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString(callSuper=true, includeFieldNames = false) 
@EqualsAndHashCode(of = {"author", "book"}) 
@Entity(name="authors_books")
public class AuthorBook implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	private Author author;
	
	@JsonIgnore 
	@Id
	@ManyToOne
	private Book book;
	

}




