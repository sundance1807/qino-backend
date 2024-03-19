package com.qino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qino.model.Occupation;
import com.qino.model.entity.PersonEntity;
import com.qino.model.entity.ProductionMemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProductionMemberDTO {
	
	private Long personId;
	private PersonEntity person;
	@NonNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Occupation occupation;
	
	public ProductionMemberDTO(ProductionMemberEntity productionMemberEntity) {
		this.personId = productionMemberEntity.getPerson().getId();
		this.person = productionMemberEntity.getPerson();
		this.occupation = productionMemberEntity.getOccupation();
	}
	
	
}
