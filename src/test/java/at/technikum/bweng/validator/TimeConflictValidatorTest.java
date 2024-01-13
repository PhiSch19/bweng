package at.technikum.bweng.validator;

import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.entity.Room;
import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeConflictValidatorTest {

    @Test
    public void testRoomIsEmpty() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        MovieRepository movieRepository = mock(MovieRepository.class);
        TimeConflictValidator validator = new TimeConflictValidator(roomRepository, movieRepository);

        when(movieRepository.findById(any())).thenReturn(Optional.of(Movie.builder().durationMinutes(30).build()));
        when(roomRepository.findById(any())).thenReturn(Optional.of(Room.builder().shows(Collections.emptyList()).build()));

        boolean valid = validator.isValid(new ShowDto(null, LocalDateTime.of(2023, 10, 24, 11, 20), UUID.randomUUID(), UUID.randomUUID(), null, null), null);
        Assert.assertTrue(valid);

    }

    @Test
    public void testWhenMovieIsAlreadyStarted() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        MovieRepository movieRepository = mock(MovieRepository.class);
        TimeConflictValidator validator = new TimeConflictValidator(roomRepository, movieRepository);

        when(movieRepository.findById(any())).thenReturn(Optional.of(Movie.builder().durationMinutes(30).build()));
        when(roomRepository.findById(any())).thenReturn(Optional.of(Room.builder().shows(List.of(
                Show.builder().room(Room.builder().cleaningMinutes(10).build()).startTime(LocalDateTime.of(2023, 10, 24, 11, 15)).movie(Movie.builder().durationMinutes(10).build()).build()
        )).cleaningMinutes(10).build()));

        boolean valid = validator.isValid(new ShowDto(null, LocalDateTime.of(2023, 10, 24, 11, 20), UUID.randomUUID(), UUID.randomUUID(), null, null), null);
        Assert.assertFalse(valid);

    }

    @Test
    public void testWhenMovieIsEndedButNotCleaned() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        MovieRepository movieRepository = mock(MovieRepository.class);
        TimeConflictValidator validator = new TimeConflictValidator(roomRepository, movieRepository);

        when(movieRepository.findById(any())).thenReturn(Optional.of(Movie.builder().durationMinutes(30).build()));
        when(roomRepository.findById(any())).thenReturn(Optional.of(Room.builder().shows(List.of(
                Show.builder().room(Room.builder().cleaningMinutes(10).build()).startTime(LocalDateTime.of(2023, 10, 24, 11, 0)).movie(Movie.builder().durationMinutes(10).build()).build()
        )).cleaningMinutes(10).build()));

        boolean valid = validator.isValid(new ShowDto(null, LocalDateTime.of(2023, 10, 24, 11, 19), UUID.randomUUID(), UUID.randomUUID(), null, null), null);
        Assert.assertFalse(valid);

    }

    @Test
    public void testWhenMovieIsEndedAndCleaned() {
        RoomRepository roomRepository = mock(RoomRepository.class);
        MovieRepository movieRepository = mock(MovieRepository.class);
        TimeConflictValidator validator = new TimeConflictValidator(roomRepository, movieRepository);

        when(movieRepository.findById(any())).thenReturn(Optional.of(Movie.builder().id(UUID.randomUUID()).durationMinutes(30).build()));
        when(roomRepository.findById(any())).thenReturn(Optional.of(Room.builder().id(UUID.randomUUID()).shows(List.of(
                Show.builder().id(UUID.randomUUID()).room(Room.builder().cleaningMinutes(10).build()).startTime(LocalDateTime.of(2023, 10, 24, 11, 0)).movie(Movie.builder().durationMinutes(10).build()).build()
        )).cleaningMinutes(10).build()));

        boolean valid = validator.isValid(new ShowDto(null, LocalDateTime.of(2023, 10, 24, 11, 20), UUID.randomUUID(), UUID.randomUUID(), null, null), null);
        Assert.assertTrue(valid);

    }


}
