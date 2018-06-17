
package pl.ust.bookshop.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

@JsonIgnoreProperties(ignoreUnknown = true) // from the Jackson JSON processing library to indicate that any properties 
//not bound in this type should be ignored.

// In case your variable name and key in JSON doc are not matching, you need to 
//use @JsonProperty annotation to specify the exact key of JSON document.

@Getter @Setter
@Builder @AllArgsConstructor  @NoArgsConstructor
@Where(clause = "is_deleted=false")
@ToString(callSuper=true, includeFieldNames = false)
@EqualsAndHashCode(callSuper=true)
@Entity(name="books")
public class Book extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
@Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private  long id;
  private  String isbn;
  private  String title;
  
  /*@ManyToMany
  private  Set<Author> author;*/
  
  @ManyToOne
  private  Publisher publisher;
  
  
 
  
}