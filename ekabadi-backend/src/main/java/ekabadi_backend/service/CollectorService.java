package ekabadi_backend.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import ekabadi_backend.dto.NearbyCollectorResponse;
import ekabadi_backend.model.Collector;
import ekabadi_backend.model.Scrap;
import ekabadi_backend.model.User;
import ekabadi_backend.repository.CollectorRepository;
import ekabadi_backend.repository.ScrapRepository;
import ekabadi_backend.repository.UserRepository;
import ekabadi_backend.dto.NearbyCollectorResponse;

@Service
public class CollectorService {

    private final ScrapRepository scrapRepository;
    private final CollectorRepository collectorRepository;
    private final DistanceService distanceService;
    private final UserRepository userRepository;

    public CollectorService(
            ScrapRepository scrapRepository,
            CollectorRepository collectorRepository,
            DistanceService distanceService,
            UserRepository userRepository

    ) {
        this.scrapRepository = scrapRepository;
        this.collectorRepository = collectorRepository;
        this.distanceService = distanceService;
        this.userRepository = userRepository;
    }

    public Collector createCollector(Collector collector) {

        User user = userRepository.findById(collector.getUserId()).orElseThrow(() -> new RuntimeException("User not found. Please provide a valid userId"));

        if(!"COLLECTOR".equalsIgnoreCase(user.getRole())) {
                throw new RuntimeException("only user with the collector role can register as collectors");
        }
        if(collectorRepository.existsByUserId(collector.getUserId())){
                throw new RuntimeException("A collector profile already exist for this user");
        }
        return collectorRepository.save(collector);
    }

    public List<Collector> getAvailableCollectors() {
        return collectorRepository.findByOnlineTrueAndAvailableTrue();
    }

    public Collector updateLocation(String id, Double latitude, Double longitude) {
        Collector collector = collectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collector not found"));

        collector.setLatitude(latitude);
        collector.setLongitude(longitude);

        return collectorRepository.save(collector);
    }


    // top 3 

    public List<NearbyCollectorResponse> getTop3NearbyCollectors(String scrapId) {
    Scrap scrap = scrapRepository.findById(scrapId)
            .orElseThrow(() -> new RuntimeException("Scrap Not Found"));

    double pickupLatitude = scrap.getPickupLatitude();
    double pickupLongitude = scrap.getPickupLongitude();

    List<Collector> nearbyCollectors = collectorRepository
            .findByOnlineTrueAndAvailableTrue()
            .stream()
            .filter(c -> c.getLatitude() != null
                    && c.getLongitude() != null)
            .sorted(Comparator.comparingDouble(collector ->
                    distanceService.calculateDistance(
                            pickupLatitude,
                            pickupLongitude,
                            collector.getLatitude(),
                            collector.getLongitude()
                    )))
            .limit(3)
            .toList();

    List<NearbyCollectorResponse> result = new java.util.ArrayList<>();

    int rank = 1;

    for (Collector collector : nearbyCollectors) {

        double distance = distanceService.calculateDistance(
                pickupLatitude,
                pickupLongitude,
                collector.getLatitude(),
                collector.getLongitude()
        );

        result.add(new NearbyCollectorResponse(
                rank++,
                collector.getId(),
                collector.getUserId(),
                Math.round(distance * 100.0) / 100.0,
                collector.getLatitude(),
                collector.getLongitude(),
                Boolean.TRUE.equals(collector.getOnline()),
                Boolean.TRUE.equals(collector.getAvailable())
        ));
    }

    return result;
}

    public Collector getCollectorById(String id) {
        return collectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collector not found"));
    }
}