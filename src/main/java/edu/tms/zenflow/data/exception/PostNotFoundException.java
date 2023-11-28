package edu.tms.zenflow.data.exception;

public class PostNotFoundException extends BadRequestException {
    public PostNotFoundException(String msg) {
        super(msg);
    }
}
