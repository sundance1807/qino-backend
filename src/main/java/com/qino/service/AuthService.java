package com.qino.service;

import com.qino.model.Role;
import com.qino.model.dto.AuthDto;
import com.qino.model.entity.UserEntity;
import com.qino.repository.RoleRepository;
import com.qino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(AuthDto authDto) {
        if (userRepository.existsByUsername(authDto.getUsername().trim())) {
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(authDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(authDto.getPassword()));
        Role roles = roleRepository.findByName("user").get();
        userEntity.setRoles(Collections.singletonList(roles));
        userRepository.save(userEntity);

        return new ResponseEntity<>("User registered success.", HttpStatus.OK);
    }
}
