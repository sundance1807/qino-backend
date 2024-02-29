package com.qino.service;

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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UsernameValidator userValidator;

    public UserDTO saveOne(UserDTO userDTO) throws CustomException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.USERNAME_IS_ALREADY_TAKEN.getText(userDTO.getUsername()))
                .build();
        };

        if (!userValidator.isValid(userDTO.getUsername())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.INVALID_USERNAME_FORMAT.getText(userDTO.getUsername()))
                .build();
        }

        userDTO.setUsername(userDTO.getUsername());
        userDTO.setPassword(userDTO.getPassword());
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO findOne(Long id) throws CustomException {
        UserEntity userEntity = findById(id);

        return modelMapper.map(userEntity, UserDTO.class);
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

    public Set<UserDTO> saveAll(Set<UserDTO> userDTOSet) throws CustomException {
        Set<UserDTO> savedUsers = new HashSet<>();
        for (UserDTO user : userDTOSet) {
            UserDTO savedUser = saveOne(user);
            savedUsers.add(savedUser);
        }
        return savedUsers;
    }

    public Set<UserDTO> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toSet());
    }

    public UserEntity findById(Long id) throws CustomException {
        return userRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                .build());
    }


}

