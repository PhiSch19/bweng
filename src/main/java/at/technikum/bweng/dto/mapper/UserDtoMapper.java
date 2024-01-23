package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDetailsDtoMapper.class)
public interface UserDtoMapper {

    @Mapping(source = "credentials", target = ".")
    @Mapping(source = "details", target = ".")
    @Mapping(source = "credentials.username", target = "username")
    User from(UserDto dto);

    @InheritInverseConfiguration
    UserDto from(User user);

}
