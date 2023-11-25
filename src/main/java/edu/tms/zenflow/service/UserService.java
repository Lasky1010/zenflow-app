package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.UserDto;

public interface UserService {
    String auth(UserDto userDto);
}
