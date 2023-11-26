package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.user.UserDto;

public interface UserService {
    String auth(UserDto userDto);

    UserSignInDto signIn(UserSignInDto user);
}
