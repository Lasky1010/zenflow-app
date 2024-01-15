package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.dto.user.UserSignUpDto;
import edu.tms.zenflow.data.dto.user.UserUpdateDto;

import java.security.Principal;

public interface UserService {

    UserSignUpDto signUp(UserSignUpDto user);

    UserDto getCurrentUser(Principal principal);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

    UserDto subscribe(Long id, Principal principal);

    UserUpdateDto update(UserUpdateDto userUpdate, Principal principal);
}
