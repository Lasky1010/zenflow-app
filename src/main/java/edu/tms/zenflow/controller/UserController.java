package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.service.UserService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto currentUser = userService.getCurrentUser(principal);
        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(Long userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

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
