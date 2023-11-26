package edu.tms.zenflow.controller;


import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/sign-in")
    public ResponseEntity<UserSignInDto> signIn(@RequestBody UserSignInDto user) {
        var regUser = userService.signIn(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(regUser);
    }


    @PostMapping
    public ResponseEntity<String> auth(@RequestBody UserDto userDto) {
        var save = userService.auth(userDto);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/private")
    public ResponseEntity<Void> privateGo() {
        return ResponseEntity.ok().build();
    }
}
