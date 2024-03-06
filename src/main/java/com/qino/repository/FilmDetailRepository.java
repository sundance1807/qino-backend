package com.qino.repository;

import com.qino.model.entity.FilmDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmDetailRepository extends JpaRepository<FilmDetailEntity, Long> {
}
