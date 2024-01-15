package at.technikum.bweng.controller.movietests;

import at.technikum.bweng.controller.MovieController;
import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.service.MovieService;
import at.technikum.bweng.storage.FileStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieControllerIT {

    private MockMvc mockMvc;

    @Mock
    private MovieRepository movieRepository;


    @MockBean
    private FileStorage fileStorage;

    @Mock
    private MovieService movieService;

    @Before
    public void setUp() {
        MovieService movieService = new MovieService(movieRepository, fileStorage);
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService, new MovieDtoMapperImpl())).build();
    }

    @Test
    public void testUploadCover() throws Exception {
        // Mock data
        UUID movieId = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        MultipartFile mockFile = mock(MultipartFile.class);

        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        // Mocking the service method
        when(movieService.upload(any(UUID.class), any(MultipartFile.class))).thenReturn(new Movie());

        // Perform the test using MockMvc
        mockMvc.perform(
                        multipart("/movie/{id}/cover", movieId)
                                .file("file", mockFile.getBytes()) // replace with your file content
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveCover() throws Exception {
        UUID movieId = UUID.randomUUID();
        String mockMediaType = "image/jpeg";  // Replace with your expected media type
        byte[] mockCoverBytes = "Mock cover image bytes".getBytes();

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");
        movie.setCoverId(movieId.toString());
        movie.setCoverContentType(mockMediaType);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(fileStorage.load(anyString())).thenReturn(new ByteArrayInputStream(mockCoverBytes));

        // Mocking the service methods
        when(movieService.getCoverMediaType(movieId)).thenReturn(MediaType.parseMediaType(mockMediaType));
        when(movieService.getCover(movieId)).thenReturn(getMockResource(mockCoverBytes));

        // Perform the test using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/movie/{id}/cover", movieId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.parseMediaType(mockMediaType)))
                .andExpect(MockMvcResultMatchers.content().bytes(mockCoverBytes));
    }

    private Resource getMockResource(byte[] content) {
        return new Resource() {
            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(content);
            }

            @Override
            public boolean exists() {
                return true;
            }

            @Override
            public URL getURL() throws IOException {
                return null;
            }

            @Override
            public URI getURI() throws IOException {
                return null;
            }

            @Override
            public File getFile() throws IOException {
                return null;
            }

            @Override
            public long contentLength() {
                return content.length;
            }

            @Override
            public long lastModified() throws IOException {
                return 0;
            }

            @Override
            public Resource createRelative(String relativePath) throws IOException {
                return null;
            }

            @Override
            public String getFilename() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            // Implement other Resource methods as needed
        };
    }

    @Test
    public void testGetMovies() throws Exception {
        Movie movie = new Movie();
        movie.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        when(movieRepository.findAll()).thenReturn(List.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/movie")
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

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.get("/movie/{id}", id)
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

        when(movieRepository.save(any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.post("/movie")
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

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.patch("/movie/{id}", id)
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

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any())).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.put("/movie/{id}", id)
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

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        mockMvc.perform(MockMvcRequestBuilders.delete("/movie/{id}", id))
                .andExpect(status().isOk());
    }

}
