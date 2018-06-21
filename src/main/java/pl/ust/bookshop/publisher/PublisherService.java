package pl.ust.bookshop.publisher;

import java.util.Set;

public interface PublisherService {
	
    void savePublisher(Publisher publisher);
    void deletePublisherById(long publisherId);
    Publisher findPublisherById(long id);
    Set<Publisher> findAllPublishers();
	boolean isPublisherExist(Publisher publisher); 
    
    /*
    void updatePublisher(Publisher publisher);
    Publisher findByName(String publisherName);
    void deleteAllPublishers();
     
*/
}
