package edu.tms.zenflow.data.mapper;

import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.dto.user.UserSignInDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.enums.Authorities;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto mapTo(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User mapTo(UserDto userDto);

    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "permissions", expression = "java(setDefaultPerm())")
    User mapTo(UserSignInDto user);

    @Mapping(target = "password", ignore = true)
    UserSignInDto mapToSignIn(User user);


    default Set<Authorities> setDefaultPerm() {
        Set<Authorities> authorities = new HashSet<>();
        authorities.add(Authorities.ROLE_USER);
        return authorities;
    }

    @Named(value = "encodePassword")
    default String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
