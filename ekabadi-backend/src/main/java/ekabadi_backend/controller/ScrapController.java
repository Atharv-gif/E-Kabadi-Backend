package ekabadi_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ekabadi_backend.model.Scrap;
import ekabadi_backend.service.ScrapService;

@RestController 
@RequestMapping ("/api/scraps")
public class ScrapController {

    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService){
        this.scrapService = scrapService;
    }


    @PostMapping
    public Scrap createScrap(@RequestBody Scrap scrap){
        return scrapService.createScrap(scrap);
    }
    
}
