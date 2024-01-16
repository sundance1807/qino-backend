package com.qino.service;

import com.qino.config.exception.CustomException;
import com.qino.model.dto.UserDto;
import com.qino.model.entity.UserEntity;
import com.qino.repository.UserRepository;
import com.qino.util.ErrorMessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserDto saveOne(UserDto userDto) throws CustomException {
        userDto.setUsername(userDto.getSecondName());
        userDto.setPassword(hash(userDto.getPassword()));
        userDto.setFirstName(userDto.getFirstName());
        userDto.setSecondName(userDto.getSecondName());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto getOne(Long id) throws CustomException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(ErrorMessageSource.USER_NOT_FOUND.getText(String.valueOf(id)))
                        .build());
        return modelMapper.map(userEntity, UserDto.class);
    }



    public String hash(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
