package com.qino.model.entity;

import com.qino.model.Occupation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "production_members")
@EqualsAndHashCode(callSuper = true)
public class ProductionMemberEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "occupation")
	private Occupation occupation;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private PersonEntity person;
}
