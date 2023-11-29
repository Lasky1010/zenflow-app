package edu.tms.zenflow.data.exception;

public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
