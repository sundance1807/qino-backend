package com.qino.repository;

import com.qino.exception.CustomException;
import com.qino.model.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
    Optional<CareerEntity> findByName(String name) throws CustomException;
}
