package edu.tms.zenflow.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;

@Service
public class ResponseErrorValidation {

    public ResponseEntity<Object> getErrors(BindingResult result) {

        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            for (ObjectError err : result.getAllErrors()) {
                errors.put(err.getCode(), err.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
