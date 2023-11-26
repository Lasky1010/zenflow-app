package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.data.entity.User;

import java.security.Principal;

public interface UserService {

    UserSignInDto signUp(UserSignInDto user);

    User getCurrentUser(Principal principal);

    UserUpdateDto update(UserUpdateDto userUpdate, Principal principal);
}
