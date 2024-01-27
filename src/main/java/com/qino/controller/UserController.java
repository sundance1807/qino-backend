package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.UserDto;
import com.qino.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveOne(@RequestBody UserDto userDto) throws CustomException {
        log.info("Incoming request to save user: {}", userDto.toString());
        return userService.saveOne(userDto);
    }


    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable Long id) throws CustomException {
        log.info("Incoming request to get user by id: {}", id);
        return userService.findById(id);
    }

    @GetMapping
    public Set<UserDto> getAll() throws CustomException {
        log.info("Incoming request to get all users");
        return userService.getAll();
    }

    public UserDto updateOne(@PathVariable Long id, @RequestBody UserDto userDto) throws CustomException {
        log.info("Incoming request to update user: {}", userDto.toString());
        return userService.updateOne(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) throws CustomException {
        log.info("Incoming request to delete user by id: {}", id);
        userService.deleteOne(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() throws CustomException {
        log.info("Incoming request to delete all users");
        userService.deleteAll();
    }
}
