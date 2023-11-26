package edu.tms.zenflow.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LogInException extends BadRequestException {
    public LogInException(String msg) {
        super(msg);
    }
}
