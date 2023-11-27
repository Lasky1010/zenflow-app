package edu.tms.zenflow.data.exception;

public class EmailHasAlreadyTakenException extends BadRequestException {
    public EmailHasAlreadyTakenException(String msg) {
        super(msg);
    }
}
