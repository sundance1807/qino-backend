package com.qino.service;

import com.github.javafaker.Faker;
import com.qino.exception.CustomException;
import com.qino.model.dto.PersonDTO;
import com.qino.model.entity.PersonEntity;
import com.qino.repository.PersonRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
	private PersonRepository personRepository;
	private ModelMapper modelMapper;
	private Faker faker;
	
	
	/**
	 * @param total how many persons do you need?
	 */
	public void generate(Integer total) {
		for (int i = 0; i < total; i++) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setFirstName(faker.name().firstName());
			personDTO.setSecondName(faker.name().lastName());
			personDTO.setDateOfBirth(LocalDate.parse("1990-01-01"));
			personDTO.setAge(calculateAge(personDTO.getDateOfBirth()));
			
			personRepository.save(modelMapper.map(personDTO, PersonEntity.class));
		}
	}
	
	/**
	 * @param personDTO person to save
	 * @return saved person
	 */
	public PersonDTO saveOne(PersonDTO personDTO) {
		personDTO.setAge(calculateAge(personDTO.getDateOfBirth()));
		PersonEntity personEntity = personRepository.save(modelMapper.map(personDTO, PersonEntity.class));
		
		return modelMapper.map(personEntity, PersonDTO.class);
	}
	
	/**
	 * @param id person id
	 * @return existing person
	 * @throws CustomException if person does not exist
	 */
	public PersonDTO findOne(Long id) throws CustomException {
		PersonEntity personEntity = findById(id);
		personEntity.setAge(calculateAge(personEntity.getDateOfBirth()));
		
		return modelMapper.map(personEntity, PersonDTO.class);
	}
	
	public Set<PersonDTO> getAllPersons() {
		return personRepository.findAll()
			.stream()
			.map(PersonDTO::new)
			.collect(Collectors.toSet());
	}
	
	/**
	 * @param id        person id
	 * @param personDTO person data to update
	 * @return updated person
	 * @throws CustomException if person does not exist
	 */
	public PersonDTO updateOne(Long id, PersonDTO personDTO) throws CustomException {
		PersonEntity personEntity = findById(id);
		personEntity.setFirstName(personDTO.getFirstName());
		personEntity.setSecondName(personDTO.getSecondName());
		personEntity.setDateOfBirth(personDTO.getDateOfBirth());
		personEntity.setAge(calculateAge(personDTO.getDateOfBirth()));
		personEntity = personRepository.save(personEntity);
		
		return modelMapper.map(personEntity, PersonDTO.class);
	}
	
	/**
	 * @param id person id
	 * @throws CustomException if person does not exist
	 */
	public void deleteOne(Long id) throws CustomException {
		PersonEntity personEntity = findById(id);
		personRepository.delete(personEntity);
	}
	
	/**
	 * @param id person id
	 * @return existing person
	 * @throws CustomException if person does not exist
	 */
	public PersonEntity findById(Long id) throws CustomException {
		return personRepository.findById(id).orElseThrow(
			() -> CustomException.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(MessageSource.PERSON_NOT_FOUND.getText(id.toString()))
				.build());
	}
	
	/**
	 * @param dateOfBirth person's date of birth
	 * @return age by date of birth
	 */
	private int calculateAge(LocalDate dateOfBirth) {
		if (dateOfBirth != null) {
			return Period.between(dateOfBirth, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}
}
