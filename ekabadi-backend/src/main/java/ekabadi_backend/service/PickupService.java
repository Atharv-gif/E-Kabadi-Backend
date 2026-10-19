package ekabadi_backend.service;

import ekabadi_backend.model.Collector;
import ekabadi_backend.model.Pickup;
import ekabadi_backend.model.Scrap;
import ekabadi_backend.repository.CollectorRepository;
import ekabadi_backend.repository.PickupRepository;
import ekabadi_backend.repository.ScrapRepository;
import org.springframework.stereotype.Service;

@Service 
public class PickupService {

    private final CollectorRepository collectorRepository;
    private final ScrapRepository scrapRepository;
    private final PickupRepository pickupRepository;

    public PickupService(PickupRepository pickupRepository, ScrapRepository scrapRepository, CollectorRepository collectorRepository) {
        this.pickupRepository = pickupRepository;
        this.scrapRepository = scrapRepository;
        this.collectorRepository = collectorRepository;
    }

    public Pickup createPickup(Pickup pickup) {
        // checking for scrap 
        Scrap scrap = scrapRepository.findById(pickup.getScrapId())
                .orElseThrow(() -> new RuntimeException("Scrap not found"));

        // checking for collector 
        Collector collector = collectorRepository.findById(pickup.getCollectorId())
                .orElseThrow(() -> new RuntimeException("Collector not found"));

        // if collector is not online / available, then throw error
        if (!Boolean.TRUE.equals(collector.getOnline()) || !Boolean.TRUE.equals(collector.getAvailable())) {
            throw new RuntimeException("Collector is offline or unavailable");
        }

        if(pickupRepository.existsByScrapIdAndStatusIn(pickup.getScrapId(), java.util.List.of("REQUESTED","ACCEPTED"))){
            throw new RuntimeException("This scrap already has an active pickup");
        }

        pickup.setCitizenId(scrap.getUserId());
        pickup.setStatus("REQUESTED");

        return pickupRepository.save(pickup);
    }

    public Pickup updatePickupStatus(String pickupId, String status) {
        status = status == null ? "" : status.trim().toUpperCase();

        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new RuntimeException("Pickup not found")); 

        if (!"ACCEPTED".equals(status) && !"REJECTED".equals(status)) {
            throw new RuntimeException("Invalid status. Use ACCEPTED or REJECTED");
        }

        if (!"REQUESTED".equals(pickup.getStatus())) {
            throw new RuntimeException("Only REQUESTED pickups can be accepted or rejected");
        }

        if ("ACCEPTED".equals(status)) {
            Collector collector = collectorRepository.findById(pickup.getCollectorId())
                    .orElseThrow(() -> new RuntimeException("Collector not found"));

            if (!Boolean.TRUE.equals(collector.getOnline()) || !Boolean.TRUE.equals(collector.getAvailable())) {
                throw new RuntimeException("Collector is offline or unavailable");
            }

            collector.setAvailable(false);
            collectorRepository.save(collector);
        }

        pickup.setStatus(status);
        return pickupRepository.save(pickup);
    }

    public Pickup completePickup(String pickupId) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new RuntimeException("Pickup not found"));

        if (!"ACCEPTED".equals(pickup.getStatus())) {
            throw new RuntimeException("Only ACCEPTED pickups can be completed");
        }

        Collector collector = collectorRepository.findById(pickup.getCollectorId())
                .orElseThrow(() -> new RuntimeException("Collector not found"));



        pickup.setStatus("COMPLETED");
        pickupRepository.save(pickup);

        Scrap scrap = scrapRepository.findById(pickup.getScrapId()).orElseThrow(() -> new RuntimeException("Scrap not found"));

        scrap.setStatus("Collected");
        scrapRepository.save(scrap);

        collector.setAvailable(true);
        collectorRepository.save(collector);

        return pickup;
    }
}