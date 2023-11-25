package edu.tms.zenflow.mapper;

import edu.tms.zenflow.data.dto.UserDto;
import edu.tms.zenflow.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto mapTo(User user);

    @Mapping(target = "id", ignore = true)
    User mapTo(UserDto userDto);
}
