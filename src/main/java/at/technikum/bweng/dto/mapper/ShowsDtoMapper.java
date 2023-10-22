package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.entity.Show;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowsDtoMapper {

    @Mapping(source = "movieId", target = "movie.id")
    @Mapping(source = "roomId", target = "room.id")
    Show from(ShowDto dto);

    @InheritInverseConfiguration
    ShowDto from(Show show);

}
