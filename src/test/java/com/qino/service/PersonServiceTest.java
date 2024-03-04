package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.PersonDTO;
import com.qino.model.entity.PersonEntity;
import com.qino.repository.PersonRepository;
import com.qino.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    private static final Long ID = 1L;
    @Mock
    private PersonRepository personRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private PersonService underTest;

    @BeforeEach
    void beforeEach() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void saveOne_savesPerson() {
        //given
        PersonDTO personDTO = Instancio.create(PersonDTO.class);
        PersonEntity personEntity = modelMapper.map(personDTO, PersonEntity.class);
        when(personRepository.save(any())).thenReturn(personEntity);
        //when
        PersonDTO savedPersonDTO = underTest.saveOne(Instancio.create(PersonDTO.class));
        //then
        verify(personRepository).save(any());
        assertNotNull(savedPersonDTO);
    }

    @Test
    void findOne_throwsException_whenPersonNotFound() {
        //given
        PersonDTO personDTO = Instancio.create(PersonDTO.class);
        PersonEntity personEntity = modelMapper.map(personDTO, PersonEntity.class);
        when(personRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findOne(ID));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.PERSON_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void findOne_findsPerson() throws CustomException {
        //given
        PersonDTO personDTO = Instancio.create(PersonDTO.class);
        PersonEntity personEntity = modelMapper.map(personDTO, PersonEntity.class);
        PersonEntity existingPersonEntity = Instancio.create(PersonEntity.class);
        when(personRepository.findById(ID)).thenReturn(Optional.of(existingPersonEntity));
        when(personRepository.save(any())).thenReturn(personEntity);
        //when
        PersonDTO updatedPersonDTO = underTest.updateOne(ID, personDTO);
        //then
        assertNotNull(updatedPersonDTO);
    }

    @Test
    void updateOne_() {
    }

    @Test
    void deleteOne() {
    }
}