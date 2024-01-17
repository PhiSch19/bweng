package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapper;
import at.technikum.bweng.exception.StorageException;
import at.technikum.bweng.security.roles.Public;
import at.technikum.bweng.security.roles.Staff;
import at.technikum.bweng.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final MovieDtoMapper dtoMapper;

    @GetMapping
    @Public
    public List<MovieDto> getMovies(@RequestParam(required = false) boolean sortByNewest) {
        return movieService.getAll(sortByNewest).stream().map(dtoMapper::from).toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Public
    public MovieDto getMovie(@PathVariable UUID id) {
        return dtoMapper.from(movieService.findMovie(id));
    }

    @PostMapping("/{id}/cover")
    @Staff
    public MovieDto uploadCover(@PathVariable UUID id, @RequestParam("file") MultipartFile toUpload) throws StorageException {
        return dtoMapper.from(movieService.upload(id, toUpload));
    }

    @GetMapping("/{id}/cover")
    @Public
    public ResponseEntity<Resource> retrieveCover(@PathVariable UUID id) throws StorageException {
        Resource resource = this.movieService.getCover(id);
        MediaType mediaType = this.movieService.getCoverMediaType(id);

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(resource);
    }

    @PostMapping
    @Staff
    public MovieDto postMovie(@RequestBody @Validated MovieDto movie) {
        if (movie.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return dtoMapper.from(movieService.addMovie(dtoMapper.from(movie)));
    }

    @PatchMapping("/{id}")
    @Staff
    public MovieDto patchMovie(@PathVariable UUID id, @RequestBody MovieDto movie) {
        if (movie.id() != null) {
            throw new IllegalArgumentException("ID update not allowed!");
        }
        return dtoMapper.from(movieService.patchMovie(id, dtoMapper.from(movie)));
    }

    @PutMapping("/{id}")
    @Staff
    public MovieDto putMovie(@PathVariable UUID id, @RequestBody @Validated MovieDto movie) {
        if (!id.equals(movie.id())) {
            throw new IllegalArgumentException("Put not allowed! IDs do not match.");
        }
        return dtoMapper.from(movieService.updateMovie(id, dtoMapper.from(movie)));
    }

    @DeleteMapping("/{id}")
    @Staff
    public void deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
    }
}
