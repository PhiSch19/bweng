package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.UserDetailsDto;
import at.technikum.bweng.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsDtoMapper {

    @Mapping(source = "profilePictureId", target = "profilePicture.id")
    User from(UserDetailsDto dto);
    // added mapping so the detail dto delivers username and role
    @Mapping(source = "username", target = "username")
    @Mapping(source = "role", target="role")
    @Mapping(source = "active", target="active")
    @InheritInverseConfiguration
    UserDetailsDto from(User user);

}
