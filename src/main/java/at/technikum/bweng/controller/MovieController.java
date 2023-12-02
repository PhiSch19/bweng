package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapper;
import at.technikum.bweng.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<MovieDto> getMovies() {
        return movieService.getAll().stream().map(dtoMapper::from).toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MovieDto getMovie(@PathVariable UUID id) {
        return dtoMapper.from(movieService.findMovie(id));
    }

    @PostMapping
    public MovieDto postMovie(@RequestBody @Validated MovieDto movie) {
        if (movie.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return dtoMapper.from(movieService.addMovie(dtoMapper.from(movie)));
    }

    @PatchMapping("/{id}")
    public MovieDto patchMovie(@PathVariable UUID id, @RequestBody MovieDto movie) {
        if (movie.id() != null) {
            throw new IllegalArgumentException("ID update not allowed!");
        }
        return dtoMapper.from(movieService.patchMovie(id, dtoMapper.from(movie)));
    }

    @PutMapping("/{id}")
    public MovieDto putMovie(@PathVariable UUID id, @RequestBody @Validated MovieDto movie) {
        if (!id.equals(movie.id())) {
            throw new IllegalArgumentException("Put not allowed! IDs do not match.");
        }
        return dtoMapper.from(movieService.updateMovie(id, dtoMapper.from(movie)));
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
    }
}
