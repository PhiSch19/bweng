package at.technikum.bweng.controller;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class MovieController {

    private final MovieService movieService;
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> getMovies(){
        return this.movieService.getAll();
    }
    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable UUID id){
        return this.movieService.findMovie(id);
    }
    @PostMapping("/movies")
    public Movie postMovie(@RequestBody Movie movie){
        return this.movieService.addMovie(movie);
    }

}
