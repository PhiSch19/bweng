package at.technikum.bweng.service;

import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> findAllShows() {
        return this.showRepository.findAll();
    }

    public Show getShow(UUID id) {
        return this.showRepository.findById(id).orElseThrow();
    }

    public Show addShow(Show show) {

        // this needs some checks
        // if not show for this date and room exists -> POST
        // if show for this date and room exist: check if there are time collisions. if not -> POST

        return this.showRepository.save(show);
    }

    public Show updateShow(UUID id, Show updatedShow) {
        getShow(id);

        return this.showRepository.save(updatedShow);
    }

    public void deleteShow(UUID id) {
        getShow(id);

        this.showRepository.deleteById(id);
    }
}
