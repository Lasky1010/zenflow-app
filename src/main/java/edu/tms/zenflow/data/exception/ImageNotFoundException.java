package edu.tms.zenflow.data.exception;

public class ImageNotFoundException extends BadRequestException {
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
