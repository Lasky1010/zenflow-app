package edu.tms.zenflow.controller;


import edu.tms.zenflow.data.dto.UserDto;
import edu.tms.zenflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody UserDto userDto) {
        var save = userService.auth(userDto);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/private")
    public ResponseEntity<Void> privateGo() {
        return ResponseEntity.ok().build();
    }
}
