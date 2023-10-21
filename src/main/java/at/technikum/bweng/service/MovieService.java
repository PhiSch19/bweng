package at.technikum.bweng.service;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAll(){
        return this.movieRepository.findAll();
    }

    public Movie findMovie(UUID id){
        return this.movieRepository.findById(id).orElseThrow();
    }

    public Movie addMovie(Movie movie){
        return this.movieRepository.save(movie);
    }

}

