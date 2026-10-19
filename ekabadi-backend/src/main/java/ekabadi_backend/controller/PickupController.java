package ekabadi_backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ekabadi_backend.model.Pickup;
import ekabadi_backend.service.PickupService;

@RestController 
@RequestMapping ("/api/pickups")
public class PickupController {


    private final PickupService pickupService;


    public PickupController(PickupService pickupService){
        this.pickupService = pickupService;
    }

    @PostMapping
    public Pickup createPickup(@RequestBody Pickup pickup){
        return pickupService.createPickup(pickup);
    }
    

    @PutMapping("/{pickupId}/status")
    public Pickup updatePickupStatus(
        @PathVariable String pickupId,
        @RequestParam String status
    ){
        return pickupService.updatePickupStatus(pickupId, status);
    }

    @PutMapping("/{pickupId}/complete")
    public Pickup completePickup(@PathVariable String pickupId) {
        return pickupService.completePickup(pickupId);
    }
}
