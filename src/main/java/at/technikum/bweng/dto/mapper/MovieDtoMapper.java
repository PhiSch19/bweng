package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.entity.Movie;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {

    @Mapping(target = "shows", ignore = true)
    Movie from(MovieDto dto);

    @InheritInverseConfiguration
    MovieDto from(Movie movie);

}
