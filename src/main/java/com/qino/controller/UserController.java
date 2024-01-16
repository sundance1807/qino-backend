package com.qino.controller;

import com.qino.config.exception.CustomException;
import com.qino.model.dto.UserDto;
import com.qino.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    public UserDto saveOne(@RequestBody UserDto userDto) throws CustomException {
        log.info("Incoming request to save user: {}", userDto.toString());
        return userService.saveOne(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable Long id) throws CustomException {
        log.info("Incoming request to get user by id: {}", id);
        return userService.getOne(id);
    }
}
