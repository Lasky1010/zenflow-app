package edu.tms.zenflow.data.exception;

public class CommentNotFoundException extends BadRequestException {

    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
