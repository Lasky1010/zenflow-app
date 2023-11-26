package edu.tms.zenflow.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordConfirmException extends BadRequestException {
    public PasswordConfirmException(String message) {
        super(message);
    }
}
