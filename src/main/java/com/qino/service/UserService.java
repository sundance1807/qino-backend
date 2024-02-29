package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.UserDTO;
import com.qino.model.entity.UserEntity;
import com.qino.repository.UserRepository;
import com.qino.util.Generator;
import com.qino.util.MessageSource;
import com.qino.util.UserValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;


    public UserDTO saveOne(UserDTO userDto) throws CustomException {
        isExist(userDto.getUsername());
        if (!userValidator.usernameIsValid(userDto.getUsername())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.INVALID_USERNAME_FORMAT.getText())
                .build();
        }
        userDto.setUsername(userDto.getUsername());
        userDto.setPassword(userDto.getPassword());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public Set<UserDTO> getAll() {
        return userRepository.findAll()
            .stream()
            .map(UserDTO::new)
            .collect(Collectors.toSet());
    }

    public UserDTO updateOne(Long id, UserDTO userDto) throws CustomException {
        UserEntity userEntity = findById(id);
        userEntity.setId(id);
        userEntity.setUsername(userDto.getUsername());
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public void deleteOne(Long id) throws CustomException {
        UserEntity userEntity = findById(id);

        userRepository.delete(userEntity);
    }

    /**
     * Deletes all users
     */
    public void deleteAll() {
        List<UserEntity> userList = userRepository.findAll();
        userRepository.deleteAll(userList);
    }

    /**
     *
     * @param id user id
     * @return existing user
     * @throws CustomException if any validation fails
     */
    public UserEntity findById(Long id) throws CustomException {
        return userRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                .build());
    }

    /**
     *
     * @param username user's username
     * @throws CustomException if any validation fails
     */
    private void isExist(String username) throws CustomException {
        if (userRepository.existsByUsername(username.toLowerCase().trim())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.FOUND)
                .message(MessageSource.USERNAME_IS_ALREADY_TAKEN.getText(username))
                .build();
        }
    }
}
