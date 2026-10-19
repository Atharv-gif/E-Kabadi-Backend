package ekabadi_backend.repository;

import ekabadi_backend.model.Collector;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectorRepository extends MongoRepository<Collector, String> {

    boolean existsByUserId(String userId);

    List<Collector> findByOnlineTrueAndAvailableTrue();

}