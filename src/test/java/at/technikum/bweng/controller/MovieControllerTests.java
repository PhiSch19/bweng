package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapper;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.entity.Show;
import at.technikum.bweng.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieControllerTests {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieDtoMapper movieDtoMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService, movieDtoMapper)).build();
    }

    @Test
    public void testGetMovies() throws Exception {
        // Arrange
        var movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<Show>());
        movie.setName("Test Movie");

        Mockito.when(movieService.getAll()).thenReturn(new ArrayList<Movie>());
        Mockito.when(movieDtoMapper.from((MovieDto) Mockito.any())).thenReturn(new Movie());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }
}
