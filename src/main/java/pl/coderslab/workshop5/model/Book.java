
package pl.coderslab.workshop5.model;

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // from the Jackson JSON processing library to indicate that any properties 
//not bound in this type should be ignored.

// In case your variable name and key in JSON doc are not matching, you need to 
//use @JsonProperty annotation to specify the exact key of JSON document.
public class Book {
  private  long id;
  private final String isbn;
  
  private final String title;
  private final String author;
  private final String publisher;
  private final String type;
  private static final AtomicLong counter = new AtomicLong(1);
  // guarantee that the value can be used in a concurrent environment
  /*The Java volatile keyword is used to mark a Java variable as "being stored in main memory". More precisely 
  that means, that every read of a volatile variable will be read from the computer's main memory, and not 
  from the CPU cache, and that every write to a volatile variable will be written to main memory, and
  not just to the CPU cache.*/
 
  private Book(BookBuilder builder){
    this.id = builder.id;
    this.isbn = builder.isbn;
    this.title = builder.title;
    this.author = builder.author;
    this.publisher = builder.publisher;
    this.type = builder.type;
  }
  
  public Book(){
    Book book = new Book.BookBuilder().id().build();
      this.id = book.getId();
      this.isbn = book.getIsbn();
      this.title = book.getTitle();
      this.author = book.getAuthor();
      this.publisher = book.getPublisher();
      this.type = book.getType();
  }
  
  public Book(long id, String isbn, String title,
      String author, String publisher, String type){
      Book book = new Book.BookBuilder().id()
           .isbn(isbn)
           .title(title)
           .author(author)
           .publisher(publisher)
           .type(type)
           .build();
      this.id = book.getId();
      this.isbn = book.getIsbn();
      this.title = book.getTitle();
      this.author = book.getAuthor();
      this.publisher = book.getPublisher();
      this.type = book.getType();

  }
  
  public long setId(long id){
	    return this.id = id;
	  }
  
  public long getId(){
    return this.id;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public String getTitle() {
    return this.title;
  }
  
  public String getAuthor(){
    return this.author;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public String getType() {
    return this.type;
  } 
  
  

  
  @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Book other = (Book) obj;
	if (id != other.id)
		return false;
	return true;
}

@Override
  public String toString(){
    return "ID: " + id 
        + " First: " + isbn
        + " Last: " + title + "\n"
        + "author: " + author + "\n"
        + "publisher: " + publisher
        + " type: " + type;
  }  
  
  public static class BookBuilder{
    private long id;
    private String isbn = "";
    private String title = "";
    private String author = "";
    private String publisher = "";
    private String type = "";
    
    public BookBuilder id(){
      this.id = Book.counter.getAndIncrement();
      return this;
    }
    
    public BookBuilder isbn(String isbn){
      this.isbn = isbn;
      return this;
    }

    public BookBuilder title(String title){
      this.title = title;
      return this;
    }
    
    public BookBuilder author(String author){
      this.author = author;
      return this;
    }
    
    public BookBuilder publisher(String publisher){
      this.publisher = publisher;
      return this;
    }
    
    public BookBuilder type(String type){
      this.type = type;
      return this;
    }

    public Book build(){
      return new Book(this);
    }
    
  }    
}
