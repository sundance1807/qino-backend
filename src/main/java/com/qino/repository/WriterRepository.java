package com.qino.repository;

import com.qino.model.entity.cast.WriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<WriterEntity, Long> {

}
