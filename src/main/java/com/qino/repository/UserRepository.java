package com.qino.repository;

import com.qino.exception.CustomException;
import com.qino.model.dto.UserDto;
import com.qino.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
