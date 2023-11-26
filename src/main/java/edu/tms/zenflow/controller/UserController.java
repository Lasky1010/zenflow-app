package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.service.UserService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @PutMapping("/update")
    public ResponseEntity<Object> update(@Valid @RequestBody UserUpdateDto userDto, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        var updUser = userService.update(userDto, principal);
        return ResponseEntity.ok(updUser);
    }
}
