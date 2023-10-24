package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapper;
import at.technikum.bweng.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class MovieController {

    private final MovieService movieService;
    private final MovieDtoMapper movieDtoMapper;

    @GetMapping("/movies")
    public List<MovieDto> getMovies() {
        return movieService.getAll().stream().map(movieDtoMapper::from).toList();
    }

    @GetMapping("/movies/{id}")
    public MovieDto getMovie(@PathVariable UUID id) {
        return movieDtoMapper.from(movieService.findMovie(id));
    }

    @PostMapping("/movies")
    public MovieDto postMovie(@RequestBody @Validated MovieDto movie) {
        if (movie.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return movieDtoMapper.from(movieService.addMovie(movieDtoMapper.from(movie)));
    }

}
