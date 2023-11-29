package at.technikum.bweng.controller;

import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieControllerTests {

    private MockMvc mockMvc;

    @Mock
    private MovieRepository movieRepository;

    @Before
    public void setUp() {
        MovieService movieService = new MovieService(movieRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService, new MovieDtoMapperImpl())).build();
    }

    @Test
    public void testGetMovies() throws Exception {
        Movie movie = new Movie();
        movie.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Test Movie",
                                "durationMinutes": 120
                            }
                        ]
                        """));
    }

    @Test
    public void testGetMovie() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""              
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Test Movie",
                                "durationMinutes": 120
                            }
                        """));
    }

    @Test
    public void testPostMovies() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.save(Mockito.any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "name": "Test Movie",
                                     "durationMinutes": 120
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Test Movie",
                                "durationMinutes": 120
                            }
                                                
                        """));
    }

    @Test
    public void testPatchMovies() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(Mockito.any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.patch("/movies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "name": "Test Movie",
                                     "durationMinutes": 120
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Test Movie",
                                "durationMinutes": 120
                            }
                                                
                        """));
    }

    @Test
    public void testPutMovies() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(Mockito.any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.put("/movies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                     "name": "Test Movie",
                                     "durationMinutes": 120,
                                     "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Test Movie",
                                "durationMinutes": 120
                            }
                                                
                        """));
    }

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/{id}", id))
                .andExpect(status().isOk());
    }

}
