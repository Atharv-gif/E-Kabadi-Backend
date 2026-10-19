
package ekabadi_backend.repository;

import ekabadi_backend.model.Pickup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PickupRepository extends MongoRepository<Pickup, String> {

    boolean existsByScrapIdAndStatusIn(
            String scrapId,
            java.util.Collection<String> statuses
    );
}
