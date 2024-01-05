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

    @InheritInverseConfiguration
    UserDetailsDto from(User user);

}
