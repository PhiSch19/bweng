package at.technikum.bweng.service;

import at.technikum.bweng.entity.Show;
import at.technikum.bweng.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShowService {
    
    private final ShowRepository showRepository;

    public List<Show> findAllShows() {
        return this.showRepository.findAll();
    }

    public Show getShow(UUID id) {
        return this.showRepository.findById(id).orElseThrow();
    }

    public Show addShow(Show show) {
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
