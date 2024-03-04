package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.CareerDTO;
import com.qino.model.entity.CareerEntity;
import com.qino.repository.CareerRepository;
import com.qino.repository.GenreRepository;
import com.qino.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CareerServiceTest {

    @Mock
    private CareerRepository careerRepository;
    @Spy
    ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private CareerService underTest;

    private static final Long ID = 1L;
    private static final String CAREER_NAME = "CAREER";
    private static final String CAREER_RU = "CAREER";

    @Test
    void saveOne_throwsException_whenCareerAlreadyExists() throws CustomException {
        //given
        CareerDTO careerDTO = Instancio.create(CareerDTO.class);
        careerDTO.setName(CAREER_NAME);
        careerDTO.setRuTranslation(CAREER_RU);
        CareerEntity careerEntity = modelMapper.map(careerDTO, CareerEntity.class);
        when(careerRepository.findByName(careerDTO.getName())).thenReturn(Optional.of(careerEntity));
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(careerDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CAREER_ALREADY_EXIST.getText(careerDTO.getName()), exception.getMessage());
    }

    @Test
    void saveOne_savesCareer() throws CustomException {
        //given
        CareerDTO careerDTO = new CareerDTO(CAREER_NAME, CAREER_RU);
        CareerEntity careerEntity = new CareerEntity();
        careerEntity.setName(careerDTO.getName());
        careerEntity.setRuTranslation(careerDTO.getRuTranslation());
        when(careerRepository.findByName(any())).thenReturn(Optional.empty());
        when(careerRepository.save(any())).thenReturn(careerEntity);
        //when
        CareerDTO savedCareerDTO = underTest.saveOne(careerDTO);
        //then
        verify(careerRepository).save(any());
        assertNotNull(savedCareerDTO);
        assertEquals(careerDTO.getName(), savedCareerDTO.getName());
        assertEquals(careerDTO.getRuTranslation(), savedCareerDTO.getRuTranslation());
    }

    @Test
    void findOne_throwsException_whenCareerNotFound() throws CustomException {
        //given
        when(careerRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findOne(ID));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.CAREER_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void findOne_findsCareer() throws CustomException{
        //given
        CareerEntity careerEntity = Instancio.create(CareerEntity.class);
        careerEntity.setId(ID);
        when(careerRepository.findById(ID)).thenReturn(Optional.of(careerEntity));
        //when
        CareerDTO result = underTest.findOne(ID);
        //then
        verify(careerRepository).findById(ID);
        assertNotNull(result);
    }

    @Test
    void deleteOne_throwsException_whenCareerNotFound() throws CustomException {
        //given
        when(careerRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deleteOne(ID));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.CAREER_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void deleteOne_deletesCareer() throws CustomException {
        //given
        ArgumentCaptor<CareerEntity> careerEntityArgumentCaptor = ArgumentCaptor.forClass(CareerEntity.class);
        CareerEntity careerEntity = Instancio.create(CareerEntity.class);
        careerEntity.setId(ID);
        when(careerRepository.findById(ID)).thenReturn(Optional.of(careerEntity));
        //when
        underTest.deleteOne(ID);
        //then
        verify(careerRepository).delete(careerEntityArgumentCaptor.capture());
        CareerEntity deletedCareer = careerEntityArgumentCaptor.getValue();
        assertEquals(careerEntity, deletedCareer);
    }
}