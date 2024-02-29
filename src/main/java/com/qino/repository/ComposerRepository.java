package com.qino.repository;

import com.qino.model.entity.cast.ComposerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposerRepository extends JpaRepository <ComposerEntity, Long> {
}
