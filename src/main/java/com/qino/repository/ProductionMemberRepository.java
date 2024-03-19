package com.qino.repository;

import com.qino.model.entity.ProductionMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductionMemberRepository extends JpaRepository<ProductionMemberEntity, Long> {
	Set<ProductionMemberEntity> findOccupationsByPersonId(Long id);
}
