package at.technikum.bweng.controller.showtests;

import at.technikum.bweng.controller.RoomController;
import at.technikum.bweng.controller.ShowController;
import at.technikum.bweng.dto.mapper.RoomDtoMapperImpl;
import at.technikum.bweng.dto.mapper.ShowsDtoMapperImpl;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.entity.Room;
import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.RoomRepository;
import at.technikum.bweng.repository.ShowRepository;
import at.technikum.bweng.service.RoomService;
import at.technikum.bweng.service.ShowService;
import at.technikum.bweng.validator.TimeConflictValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ShowControllerIT {

    private MockMvc mockMvc;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private TimeConflictValidator timeConflictValidator;

    @Before
    public void setUp() {
        ShowService showService = new ShowService(showRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new ShowController(showService, new ShowsDtoMapperImpl())).build();
    }

    @Test
    public void testGetShows() throws Exception {
        LocalDate localDate = LocalDate.of(2023,1,1);
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(new Room());
        show.setMovie(new Movie());
        show.setStartTime(localDate.atStartOfDay());
        show.setCreatedOn(localDate.atStartOfDay());
        show.setLastUpdatedOn(localDate.atStartOfDay());

        Mockito.when(showRepository.findAll()).thenReturn(List.of(show));

        mockMvc.perform(MockMvcRequestBuilders.get("/show")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                           }
                        ]
                        """));
    }

    @Test
    public void testGetShow() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");

        LocalDate localDate = LocalDate.of(2023,1,1);
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(new Room());
        show.setMovie(new Movie());
        show.setStartTime(localDate.atStartOfDay());
        show.setCreatedOn(localDate.atStartOfDay());
        show.setLastUpdatedOn(localDate.atStartOfDay());


        Mockito.when(showRepository.findById(id)).thenReturn(Optional.of(show));

        mockMvc.perform(MockMvcRequestBuilders.get("/show/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""              
                            {
                               "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                            }
                        """));
    }

    /*
    @Test
    public void testPostShows() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        LocalDate localDate = LocalDate.of(2023,1,1);
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(new Room());
        show.setMovie(new Movie());
        show.setStartTime(localDate.atStartOfDay());
        show.setCreatedOn(localDate.atStartOfDay());
        show.setLastUpdatedOn(localDate.atStartOfDay());

        Mockito.when(showRepository.save(Mockito.any())).thenReturn(show);

        mockMvc.perform(MockMvcRequestBuilders.post("/show")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                  "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                            }
                                                
                        """));
    }*/

    /*
    @Test
    public void testPatchShows() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        LocalDate localDate = LocalDate.of(2023,1,1);
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(new Room());
        show.setMovie(new Movie());
        show.setStartTime(localDate.atStartOfDay());
        show.setCreatedOn(localDate.atStartOfDay());
        show.setLastUpdatedOn(localDate.atStartOfDay());

        Mockito.when(showRepository.findById(id)).thenReturn(Optional.of(show));
        Mockito.when(showRepository.save(Mockito.any())).thenReturn(show);

        mockMvc.perform(MockMvcRequestBuilders.patch("/show/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""

                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                            }

                        """));
    }*/

    /*
    @Test
    public void testPutShows() throws Exception {
        LocalDate localDate = LocalDate.of(2024,1,1);
        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setCreatedOn(localDate.atStartOfDay());
        room.setLastUpdatedOn(localDate.atStartOfDay());
        room.setShows(new ArrayList<>());

        Movie movie = new Movie();
        movie.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        movie.setDurationMinutes(120);
        movie.setShows(new ArrayList<>());
        movie.setName("Test Movie");

        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(room);
        show.setMovie(movie);

        Mockito.when(showRepository.findById(id)).thenReturn(Optional.of(show));
        Mockito.when(showRepository.save(Mockito.any())).thenReturn(show);

        Mockito.when(timeConflictValidator.isValid(Mockito.any(),Mockito.any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/show/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""                  
                            {
                               "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"
                            }        
                        """));
    }*/

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        LocalDate localDate = LocalDate.of(2023,1,1);
        Show show = new Show();
        show.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        show.setRoom(new Room());
        show.setMovie(new Movie());
        show.setStartTime(localDate.atStartOfDay());
        show.setCreatedOn(localDate.atStartOfDay());
        show.setLastUpdatedOn(localDate.atStartOfDay());

        Mockito.when(showRepository.findById(id)).thenReturn(Optional.of(show));

        mockMvc.perform(MockMvcRequestBuilders.delete("/show/{id}", id))
                .andExpect(status().isOk());
    }
}
