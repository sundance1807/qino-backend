package com.qino.controller;

import com.qino.model.dto.AuthDto;
import com.qino.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody AuthDto authDto) {
        log.info("Incoming request to register user: {}", authDto);
        return authService.register(authDto);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(AuthDto authDto) {
//        log.info("Incoming request to login user: {}", authDto);
//        return authService.login(authDto);
//    }
}
