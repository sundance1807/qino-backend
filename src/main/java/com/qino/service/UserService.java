package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.UserDto;
import com.qino.model.entity.UserEntity;
import com.qino.repository.UserRepository;
import com.qino.util.MessageSource;
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

    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserDto saveOne(UserDto userDto) throws CustomException {

        if (userRepository.existsByUsername(userDto.getUsername().trim())) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.FOUND)
                    .message(MessageSource.USERNAME_IS_ALREADY_TAKEN.getText(String.valueOf(userDto.getUsername())))
                    .build();
        }

        userDto.setUsername(userDto.getUsername());
        userDto.setPassword(userDto.getPassword());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto findById(Long id) throws CustomException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                        .build());
        return modelMapper.map(userEntity, UserDto.class);
    }

    public Set<UserDto> getAll() throws CustomException {
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toSet());
    }

    public UserDto updateOne(Long id, UserDto userDto) throws CustomException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                        .build());

        userDto.setId(id);
        userDto.setUsername(userDto.getUsername());
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    public void deleteOne(Long id) throws CustomException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                        .build());

        userRepository.delete(userEntity);
    }

    public void deleteAll() {
        List<UserEntity> userList = userRepository.findAll();
        userRepository.deleteAll(userList);
    }
}
