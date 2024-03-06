package com.qino.repository;

import com.qino.exception.CustomException;
import com.qino.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByName(String name) throws CustomException;

    Set<GenreEntity> findAllByIdIn(Set<Long> ids);

    Boolean existsByName(String name);
}
