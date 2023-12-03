package at.technikum.bweng.service;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final FileStorage fileStorage;

    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    public Movie upload(UUID id, MultipartFile toUpload) throws FileUploadException {
        Movie movie = findMovie(id);
        var externalId = fileStorage.upload(toUpload);

        movie.setCoverId(externalId);
        movie.setCoverContentType(toUpload.getContentType());

        return this.movieRepository.save(movie);
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

    public Resource getCover(UUID id) throws FileNotFoundException {
        var movie = findMovie(id);

        InputStream stream = fileStorage.load(movie.getCoverId());
        return new InputStreamResource(stream);
    }

    public MediaType getCoverMediaType(UUID id) {
        var movie = findMovie(id);
        return MediaType.parseMediaType(movie.getCoverContentType());
    }
}

