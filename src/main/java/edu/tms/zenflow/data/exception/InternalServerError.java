package edu.tms.zenflow.data.exception;

public class InternalServerError extends BadRequestException {

    public InternalServerError(String msg) {
        super(msg);
    }
}
