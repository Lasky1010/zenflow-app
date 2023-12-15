package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.data.dto.user.UserDto;

import java.security.Principal;

public interface UserService {

    UserSignInDto signUp(UserSignInDto user);

    UserDto getCurrentUser(Principal principal);

    UserUpdateDto update(UserUpdateDto userUpdate, Principal principal);

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

    UserDto subscribe(Long id, Principal principal);
}
