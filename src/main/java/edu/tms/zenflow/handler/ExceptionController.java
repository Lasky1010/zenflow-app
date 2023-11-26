package edu.tms.zenflow.handler;

import edu.tms.zenflow.data.dto.response.ApiResponse;
import edu.tms.zenflow.data.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleLogInException(BadRequestException e) {
        log.error("{} exception caught: {}", e.getClass().getSimpleName(), e.getMessage());
        return buildResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ApiResponse> buildResponseEntity(String message, HttpStatus httpStatus) {
        ApiResponse apiResponse = ApiResponse.createApiResponse(message, httpStatus.value());
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

}
