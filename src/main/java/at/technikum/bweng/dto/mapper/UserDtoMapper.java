package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "credentials", target = ".")
    @Mapping(source = "details", target = ".")
    @Mapping(source = "details.profilePictureId", target = "profilePicture.id")
    User from(UserDto dto);

    @InheritInverseConfiguration
    UserDto from(User user);

}
