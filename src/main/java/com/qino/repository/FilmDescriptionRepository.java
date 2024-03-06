package com.qino.repository;

import com.qino.model.entity.FilmDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmDescriptionRepository extends JpaRepository<FilmDescriptionEntity, Long> {
}
