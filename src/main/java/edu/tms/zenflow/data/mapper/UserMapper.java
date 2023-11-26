package edu.tms.zenflow.data.mapper;

import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.enums.Authorities;
import edu.tms.zenflow.validations.EmailValidator;
import edu.tms.zenflow.validations.PasswordValidator;
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

    @Mapping(target = "password", ignore = true)
    UserUpdateDto mapToUpdatedDto(User user);


    default User mapToUpdatedUser(UserUpdateDto dto, User user) {
        if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getBio() != null && !dto.getBio().isEmpty()) {
            user.setBio(dto.getBio());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            EmailValidator emailValidator = new EmailValidator();
            if (emailValidator.isValid(dto.getEmail())) {
                user.setEmail(dto.getEmail());
            }

        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            PasswordValidator passValidator = new PasswordValidator();
            if (passValidator.isValid(dto.getPassword())) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(dto.getPassword()));
            }
        }
        return user;
    }


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
