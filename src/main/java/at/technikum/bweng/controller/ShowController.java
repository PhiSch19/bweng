package at.technikum.bweng.controller;

import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.dto.mapper.ShowsDtoMapper;
import at.technikum.bweng.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class ShowController {

    private final ShowService showService;
    private final ShowsDtoMapper dtoMapper;

    @GetMapping("/shows")
    public List<ShowDto> getAll() {
        return this.showService.findAllShows().stream().map(dtoMapper::from).toList();
    }

    @GetMapping("/shows/{id}")
    public ShowDto getShow(@PathVariable UUID id) {
        return dtoMapper.from(this.showService.getShow(id));
    }

    @PostMapping("/shows")
    public ShowDto postShow(@RequestBody @Validated ShowDto show) {
        if (show.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return dtoMapper.from(this.showService.addShow(dtoMapper.from(show)));
    }

    @PutMapping("/shows/{id}")
    public ShowDto putMovie(@PathVariable UUID id, @RequestBody @Validated ShowDto show) {
        if (!id.equals(show.id())) {
            throw new IllegalArgumentException("Put not allowed! IDs do not match.");
        }
        return dtoMapper.from(showService.updateShow(id, dtoMapper.from(show)));
    }

    @DeleteMapping("/shows/{id}")
    public void deleteShow(@PathVariable UUID id) {
        this.showService.deleteShow(id);
    }
}
