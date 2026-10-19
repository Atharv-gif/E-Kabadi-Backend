
package ekabadi_backend.service;

import org.springframework.stereotype.Service;

import ekabadi_backend.model.Scrap;
import ekabadi_backend.model.User;
import ekabadi_backend.repository.ScrapRepository;
import ekabadi_backend.repository.UserRepository;

@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    public ScrapService(
            ScrapRepository scrapRepository,
            UserRepository userRepository) {
        this.scrapRepository = scrapRepository;
        this.userRepository = userRepository;
    }

    public Scrap createScrap(Scrap scrap) {

        // Check that the citizen exists
        User user = userRepository.findById(scrap.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found. Please provide a valid userId."));

        // Only citizens can create scrap
        if (!"CITIZEN".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only citizens can create scrap.");
        }

        // Save scrap only after validation succeeds
        return scrapRepository.save(scrap);
    }
}
