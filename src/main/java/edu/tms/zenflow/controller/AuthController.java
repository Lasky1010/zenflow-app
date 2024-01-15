package edu.tms.zenflow.controller;


import edu.tms.zenflow.data.dto.response.TokenResponse;
import edu.tms.zenflow.data.dto.user.UserLogInDto;
import edu.tms.zenflow.data.dto.user.UserSignUpDto;
import edu.tms.zenflow.security.AuthenticationService;
import edu.tms.zenflow.service.UserService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;
    private final AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserSignUpDto user, BindingResult bindingResult) {

        ResponseEntity<Object> errors = responseErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        var regUser = userService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(regUser);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Object> auth(@Valid @RequestBody UserLogInDto userDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        var token = new TokenResponse(authenticationService.auth(userDto));
        return ResponseEntity.ok(token);
    }

    @CrossOrigin
    @PostMapping("/private")
    public ResponseEntity<Void> privateGo() {
        return ResponseEntity.ok().build();
    }
}
