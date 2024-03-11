package com.qino.service;

import com.github.javafaker.Faker;
import com.qino.exception.CustomException;
import com.qino.model.dto.UserDTO;
import com.qino.model.entity.UserEntity;
import com.qino.repository.UserRepository;
import com.qino.util.MessageSource;
import com.qino.util.UsernameValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UsernameValidator userValidator;
    private final ModelMapper modelMapper;
    private final Faker faker;


    public void generate(Integer total) throws CustomException {
        for (int i = 0; i < total; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(faker.name().username());
            userDTO.setPassword(faker.random().hex(12));
            saveOne(userDTO);
        }
    }

    public UserDTO saveOne(UserDTO userDTO) throws CustomException {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDTO.getUsername()))) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.USERNAME_IS_ALREADY_TAKEN.getText(userDTO.getUsername()))
                .build();
        }

        if (!userValidator.isValid(userDTO.getUsername())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.INVALID_USERNAME_FORMAT.getText(userDTO.getUsername()))
                .build();
        }

        userDTO.setUsername(userDTO.getUsername().trim());
        userDTO.setPassword(userDTO.getPassword().trim());
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO findOne(Long id) throws CustomException {
        UserEntity userEntity = findById(id);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Set<UserDTO> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toSet());
    }

    public UserDTO updateOne(Long id, UserDTO userDTO) throws CustomException {
        UserEntity userEntity = findById(id);
        userEntity.setId(id);
        userEntity.setUsername(userDTO.getUsername());
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public void deleteOne(Long id) throws CustomException {
        UserEntity userEntity = findById(id);

        userRepository.delete(userEntity);
    }

    private UserEntity findById(Long id) throws CustomException {
        return userRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                .build());
    }
}

