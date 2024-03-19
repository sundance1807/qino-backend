package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.entity.ProductionMemberEntity;
import com.qino.repository.ProductionMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductionMemberService {
	private ProductionMemberRepository productionMemberRepository;
	private PersonService personService;
	
	public Set<ProductionMemberEntity> saveAll(Set<ProductionMemberEntity> productionMemberEntitySet) throws CustomException {
		Set<ProductionMemberEntity> result = new HashSet<>();
		
		for (ProductionMemberEntity productionMemberEntity : productionMemberEntitySet) {
			productionMemberEntity.setPerson(personService.findById(productionMemberEntity.getPerson().getId()));
			productionMemberEntity = productionMemberRepository.save(productionMemberEntity);
			result.add(productionMemberEntity);
		}
		
		return result;
	}
}
