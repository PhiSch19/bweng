package at.technikum.bweng.service;

import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository){
        this.showRepository = showRepository;
    }

    public List<Show> findAllShows(){
        return this.showRepository.findAll();
    }

    public Show getShow(UUID id){
        return this.showRepository.findById(id).orElseThrow();
    }

    public Show addShow(Show show) throws IllegalArgumentException{

        // validate for time conflict
        if (this.isTimeConflict(show)){
            throw new IllegalArgumentException("could not create show. Time Confict");
        }
        // this needs some checks
        // if not show for this date and room exists -> POST
        // if show for this date and room exist: check if there are time collisions. if not -> POST
        return this.showRepository.save(show);
    }

    private boolean isTimeConflict(Show show){
        /* check if there is a time collision between existing shows and show*/

        // get start time (left) and end time (right) from new show
        LocalDateTime left = show.getStartTime();
        LocalDateTime right = left.plusMinutes(show.getMovie().getDurationMinutes() + show.getRoom().getCleaningMinutes());

        // fetch list of existing shows from database
        List<Show> AllShows = this.showRepository.findAll();
        for(Show existing : AllShows){
            // check if it is the same room
            if( !show.getRoom().getId().equals(existing.getRoom().getId()) )
                continue;
            // get start time (_left) and end time (_right) from existing
            LocalDateTime _left = existing.getStartTime();
            LocalDateTime _right = _left.plusMinutes(existing.getMovie().getDurationMinutes() + existing.getRoom().getCleaningMinutes());
            // left side time conflict validation
            if( left.isAfter(_left) && !left.isAfter(_right) )
                return true;
            // right side time conflict validation
            if( right.isBefore(_right) && !right.isBefore(_left) )
                return true;
            //containing time conflict validation
            if( left.isBefore(_left) && right.isAfter(_right) )
                return true;
        }
        return false;
    };

}
