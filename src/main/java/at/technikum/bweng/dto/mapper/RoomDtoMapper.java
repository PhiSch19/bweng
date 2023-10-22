package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.entity.Room;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDtoMapper {

    @Mapping(target = "shows", ignore = true)
    Room from(RoomDto dto);

    @InheritInverseConfiguration
    RoomDto from(Room room);

}
