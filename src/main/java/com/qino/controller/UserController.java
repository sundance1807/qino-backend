package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.UserDTO;
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
    public UserDTO saveOne(@RequestBody UserDTO userDTO) throws CustomException {
        log.info("Incoming request to save user: {}.", userDTO.toString());
        return userService.saveOne(userDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserDTO getOne(@PathVariable Long id) throws CustomException {
        log.info("Incoming request to get user by id: {}.", id);
        return userService.findOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Set<UserDTO> getAll() {
        log.info("Incoming request to get all users.");
        return userService.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateOne(@PathVariable Long id, @RequestBody UserDTO userDTO) throws CustomException {
        log.info("Incoming request to update user: {}.", userDTO.toString());
        return userService.updateOne(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) throws CustomException {
        log.info("Incoming request to delete user by id: {}.", id);
        userService.deleteOne(id);
    }
}
