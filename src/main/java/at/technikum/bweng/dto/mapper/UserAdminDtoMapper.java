package at.technikum.bweng.dto.mapper;

import at.technikum.bweng.dto.UserAdminDto;
import at.technikum.bweng.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDetailsDtoMapper.class)
public interface UserAdminDtoMapper {

    @Mapping(source = "details", target = ".")
    User from(UserAdminDto dto);
    
    @InheritInverseConfiguration
    UserAdminDto from(User user);

}
