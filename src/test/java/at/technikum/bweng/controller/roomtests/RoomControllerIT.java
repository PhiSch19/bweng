package at.technikum.bweng.controller.roomtests;

import at.technikum.bweng.controller.MovieController;
import at.technikum.bweng.controller.RoomController;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.dto.mapper.RoomDtoMapperImpl;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.entity.Room;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.repository.RoomRepository;
import at.technikum.bweng.service.MovieService;
import at.technikum.bweng.service.RoomService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomControllerIT {

    private MockMvc mockMvc;

    @Mock
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        RoomService roomService = new RoomService(roomRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(roomService, new RoomDtoMapperImpl())).build();
    }

    @Test
    public void testGetRooms() throws Exception {
        LocalDate localDate = LocalDate.of(2023,1,1);
        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setCreatedOn(localDate.atStartOfDay());
        room.setLastUpdatedOn(localDate.atStartOfDay());
        room.setShows(new ArrayList<>());

        Mockito.when(roomRepository.findAll()).thenReturn(List.of(room));

        mockMvc.perform(MockMvcRequestBuilders.get("/room")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                            }
                        ]
                        """));
    }

    @Test
    public void testGetRoom() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");

        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setShows(new ArrayList<>());


        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        mockMvc.perform(MockMvcRequestBuilders.get("/room/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""              
                            {
                               "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                            }
                        """));
    }

    @Test
    public void testPostRooms() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setShows(new ArrayList<>());

        Mockito.when(roomRepository.save(Mockito.any())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                  "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                            }
                                                
                        """));
    }

    @Test
    public void testPatchRooms() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setShows(new ArrayList<>());

        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        Mockito.when(roomRepository.save(Mockito.any())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.patch("/room/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                
                            {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                            }
                                                
                        """));
    }

    @Test
    public void testPutRooms() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Room room = new Room();
        room.setId(id);
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setShows(new ArrayList<>());

        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        Mockito.when(roomRepository.save(Mockito.any())).thenReturn(room);

        mockMvc.perform(MockMvcRequestBuilders.put("/room/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                                }
                                     """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""                  
                            {
                               "id": "08e2b6ab-93a3-4acc-b4b1-d3ae344600d2",
                                "name": "Room 1",
                                "capacity": 100,
                                "cleaningMinutes": 50
                            }        
                        """));
    }

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2");
        Room room = new Room();
        room.setId(UUID.fromString("08e2b6ab-93a3-4acc-b4b1-d3ae344600d2"));
        room.setCapacity(100);
        room.setCleaningMinutes(50);
        room.setName("Room 1");
        room.setShows(new ArrayList<>());

        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        mockMvc.perform(MockMvcRequestBuilders.delete("/room/{id}", id))
                .andExpect(status().isOk());
    }
}
