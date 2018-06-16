package pl.ust.bookshop.book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
@Service("memoryBookService")
// @Transactional import org.springframework.transaction.annotation.Transactional;
public class MemoryBookService implements BookService {

	private static final AtomicLong counter = new AtomicLong();
    
    private static List<Book> list;
     
    static{
        list = populateListWithDummyBooks();
    }
 
    public List<Book> findAllBooks() {
        return list;
    }
     
    public Book findById(long id) {
        for(Book book : list){
            if(book.getId() == id){
                return book;
            }
        }
        return null;
    }
     
    public Book findByName(String name) {
        for(Book book : list){
            if(book.getTitle().equalsIgnoreCase(name)){
                return book;
            }
        }
        return null;
    }
     
    public void saveBook(Book book) {
        book.setId(counter.incrementAndGet());
        list.add(book);
    }
 
    public void updateBook(Book book) {
        int index = list.indexOf(book);
        list.set(index, book);
    }
 
    public void deleteBookById(long id) {
         
        for (Iterator<Book> iterator = list.iterator(); iterator.hasNext(); ) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
            }
        }
    }
 
    public boolean isBookExist(Book book) {
        return findByName(book.getTitle()) != null;
    }
 
    private static List<Book> populateListWithDummyBooks(){
        List<Book> books = new ArrayList<Book>();
        books.add(new Book(counter.incrementAndGet(),"1476439874525", "Thinking	in	Java", "Bruce	Eckel", "Helion", "programming"));
        books.add(new Book(counter.incrementAndGet(),"7604148757610", "Przygody Meliklesa", "Andrzej miłosz", "Publish Co", "adventure"));
        books.add(new Book(counter.incrementAndGet(),"9695298768600", "W Pustyni", "Henryk Sienkiewicz", "Mars", "travel"));
        books.add(new Book(counter.incrementAndGet(),"8731872608764", "Here and There", "Tony James", "WLC", "poetry"));
        return books;
    }
 
    public void deleteAllBooks() {
        list.clear();
    }
}
