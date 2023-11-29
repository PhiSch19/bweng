package at.technikum.bweng.service;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

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
        Movie existingMovie = findMovie(id);

        if (updatedMovie.getName() != null) {
            existingMovie.setName(updatedMovie.getName());
        }
        if (updatedMovie.getDurationMinutes() != null) {
            existingMovie.setDurationMinutes(updatedMovie.getDurationMinutes());
        }

        return updateMovie(id, existingMovie);
    }

    public Movie updateMovie(UUID id, Movie updatedRoom) {
        findMovie(id);

        return movieRepository.save(updatedRoom);
    }

    public void deleteMovie(UUID id) {
        findMovie(id);

        movieRepository.deleteById(id);
    }
}

