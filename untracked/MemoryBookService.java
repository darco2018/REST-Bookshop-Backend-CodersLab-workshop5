package pl.ust.bookshop.book;

//@Component
//@Service("memoryBookService")
// @Transactional import org.springframework.transaction.annotation.Transactional;
public class MemoryBookService {  // implements BookService {

	/*private static final AtomicLong counter = new AtomicLong();
    
    private static List<Book> list;
     
    static{
        list = populateListWithDummyBooks();
    }
 
    public Set<Book> findAllBooks() {
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
        books.add(new Book(1,"12345678910", "Thinking	in	Java", "Bruce	Eckel", "Helion", "programming"));
        books.add(new Book(2,"12345678910", "Przygody Meliklesa", "Andrzej miłosz", "Publish Co", "adventure"));
        books.add(new Book(3,"12345678910", "W Pustyni", "Henryk Sienkiewicz", "Mars", "travel"));
        books.add(new Book(4,"12345678910", "Here and There", "Tony James", "WLC", "poetry"));
        return books;
    }
 
    public void deleteAllBooks() {
        list.clear();
    }*/
}
