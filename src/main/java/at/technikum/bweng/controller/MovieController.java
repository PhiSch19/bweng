package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapper;
import at.technikum.bweng.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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
    public MovieDto postMovie(@RequestBody MovieDto movie) {
        return movieDtoMapper.from(movieService.addMovie(movieDtoMapper.from(movie)));
    }

}
