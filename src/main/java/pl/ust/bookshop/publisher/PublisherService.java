package pl.ust.bookshop.publisher;

import java.util.List;

public interface PublisherService {
	
    void savePublisher(Publisher publisher);
    void deletePublisherById(long publisherId);
    Publisher findById(long id);
    List<Publisher> findAllPublishers();
	boolean isPublisherExist(Publisher publisher); 
    
    /*
    void updatePublisher(Publisher publisher);
    Publisher findByName(String publisherName);
    void deleteAllPublishers();
     
*/
}
