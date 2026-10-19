package ekabadi_backend.repository;

import ekabadi_backend.model.Scrap;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapRepository extends MongoRepository<Scrap, String> {

}