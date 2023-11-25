package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.dto.user.UserSignInDto;

public interface UserService {
    String auth(UserDto userDto);

    UserSignInDto signIn(UserSignInDto user);
}
