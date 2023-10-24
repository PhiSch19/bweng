package at.technikum.bweng.service;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie getMovie(UUID id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    public Movie findMovie(UUID id) {
        return this.movieRepository.findById(id).orElseThrow();
    }

    public Movie addMovie(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public Movie patchMovie(UUID id, Movie updatedMovie) {
        Movie existingMovie = getMovie(id);

        if (updatedMovie.getName() != null) {
            existingMovie.setName(updatedMovie.getName());
        }
        if (updatedMovie.getShows() != null) {
            existingMovie.setShows(updatedMovie.getShows());
        }
        if (updatedMovie.getDurationMinutes() != null) {
            existingMovie.setDurationMinutes(updatedMovie.getDurationMinutes());
        }

        return movieRepository.save(existingMovie);
    }

    public Movie updateMovie(UUID id, Movie updatedRoom) {
        getMovie(id);

        return movieRepository.save(updatedRoom);
    }

    public void deleteMovie(UUID id) {
        getMovie(id);

        movieRepository.deleteById(id);
    }
}

