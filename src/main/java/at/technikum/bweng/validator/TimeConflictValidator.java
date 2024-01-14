package at.technikum.bweng.validator;

import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.entity.Room;
import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.repository.RoomRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TimeConflictValidator implements ConstraintValidator<TimeConflictConstraint, ShowDto> {

    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    @Override
    public boolean isValid(ShowDto show, ConstraintValidatorContext context) {
        Room room = roomRepository.findById(show.roomId()).orElseThrow();
        Movie movie = movieRepository.findById(show.movieId()).orElseThrow();
        LocalDateTime plannedStartTime = show.startTime();
        LocalDateTime plannedEndTime = plannedStartTime.plusMinutes(movie.getDurationMinutes());

        return room.getShows().stream()
                .filter(s -> show.id() == null || show.id() != s.getId())
                .noneMatch(s ->
                        (s.getStartTime().isBefore(plannedStartTime) && calcNextPossibleStart(s).isAfter(plannedStartTime))
                                || (s.getStartTime().isAfter(plannedStartTime) && plannedEndTime.isBefore(s.getStartTime()))
                );

    }

    private LocalDateTime calcNextPossibleStart(LocalDateTime startTime, Movie movie, Room room) {
        return startTime.plusMinutes(movie.getDurationMinutes()).plusMinutes(room.getCleaningMinutes());
    }

    private LocalDateTime calcNextPossibleStart(Show show) {
        return calcNextPossibleStart(show.getStartTime(), show.getMovie(), show.getRoom());
    }
}
