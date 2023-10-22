package at.technikum.bweng.controller;

import at.technikum.bweng.entity.Show;
import at.technikum.bweng.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ShowController {

    private final ShowService showService;
    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @GetMapping("/shows")
    public List<Show> getAll(){
        return this.showService.findAllShows();
    }
    @GetMapping("/shows/{id}")
    public Show getShow(@PathVariable UUID id){
        return this.showService.getShow(id);
    }

    @PostMapping("/shows")
    public Show postShow(@RequestBody Show show){
        return this.showService.addShow(show);
    }

}
