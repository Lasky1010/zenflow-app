package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.image.ImageDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.dto.user.UserUpdateDto;
import edu.tms.zenflow.service.ImageService;
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
    private final ImageService imageService;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto currentUser = userService.getCurrentUser(principal);

        UserDto userDto = new UserDto(currentUser);
        ImageDto imageToUser = imageService.getImageToUser(principal);
        byte[] currentUserImage = null;
        if (imageToUser != null) {
            currentUserImage = imageToUser.getImageData();
        }
        userDto.setImageData(currentUserImage);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
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

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<UserDto> subscribe(@PathVariable("id") Long userId, Principal principal) {
        UserDto user = userService.subscribe(userId, principal);
        return ResponseEntity.ok(user);
    }
}
