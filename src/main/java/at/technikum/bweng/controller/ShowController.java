package at.technikum.bweng.controller;

import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.dto.mapper.ShowsDtoMapper;
import at.technikum.bweng.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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
    public ShowDto postShow(@RequestBody ShowDto show) {
        return dtoMapper.from(this.showService.addShow(dtoMapper.from(show)));
    }

}
