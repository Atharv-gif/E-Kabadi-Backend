package ekabadi_backend.controller;

import ekabadi_backend.service.DistanceService;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ekabadi_backend.dto.NearbyCollectorResponse;
import ekabadi_backend.model.Collector;
import ekabadi_backend.service.CollectorService;

@RestController 
@RequestMapping ("/api/collectors")
public class CollectorController {





    private final DistanceService distanceService;
    private final CollectorService collectorService;


    public CollectorController(CollectorService collectorService , DistanceService distanceService){
        this.collectorService = collectorService;
        this.distanceService = distanceService;
    }
    
    // new collector 

    @PostMapping
   public Collector createCollector(@RequestBody Collector collector){
    return collectorService.createCollector(collector);
   }


   // to get which collector is currently online and available 


   @GetMapping("/available")
   public List<Collector> getAvailableCollectors(){
    return collectorService.getAvailableCollectors();
   }

   @GetMapping("/{id}")
   public Collector getCollectorById(@PathVariable String id) {
       return collectorService.getCollectorById(id);
   }




   // updation of location 

   @PutMapping("/{id}/location")
   public Collector updateLocation(
    @PathVariable String id,
    @RequestParam Double latitude,
    @RequestParam Double longitude){

        return collectorService.updateLocation(id, latitude, longitude);
    }


    @GetMapping("/distance")
        public double calculateDistance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2
        ){


            return distanceService.calculateDistance(lat1, lon1, lat2, lon2);



        }

        @GetMapping("/nearby")
        public List<NearbyCollectorResponse> getTop3Collectors(
            @RequestParam String scrapId
        ){
            return collectorService.getTop3NearbyCollectors(scrapId);
        }
   
}


