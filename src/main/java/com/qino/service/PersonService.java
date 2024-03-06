package com.qino.service;

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

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;
    private ModelMapper modelMapper;

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
        personEntity.setCareers(personDTO.getCareers());
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
    private PersonEntity findById(Long id) throws CustomException {
        return personRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.PERSON_NOT_FOUND.getText(id.toString()))
                .build());
    }

    private int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
