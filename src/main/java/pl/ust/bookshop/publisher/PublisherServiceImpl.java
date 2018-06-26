package pl.ust.bookshop.publisher;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PublisherServiceImpl implements PublisherService{
	
	private final @NonNull PublisherRepository publisherRepo;

	@Override
	public Publisher savePublisher(Publisher publisher) {
		Assert.notNull(publisher, "The author can't be null!");
		return this.publisherRepo.save(publisher);
	}

	@Override
	public void deletePublisherById(long publisherId) {
		this.publisherRepo.deleteById(publisherId);
	}

	@Override
	public Publisher findPublisherById(long id) {
		Optional<Publisher> opt = this.publisherRepo.findById(id);
		return opt.orElseThrow(IllegalArgumentException::new);
	}

	@Override
	public Set<Publisher> findAllPublishers() {
		return this.publisherRepo.findAll();
	}
	
	@Override
	public boolean isPublisherExist(Publisher publisher) {
		Assert.notNull(publisher, "The author can't be null!");
		return this.publisherRepo.existsById(publisher.getId());
	}

	

}
