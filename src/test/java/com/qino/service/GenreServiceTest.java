package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.GenreDTO;
import com.qino.model.entity.GenreEntity;
import com.qino.repository.GenreRepository;
import com.qino.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;
    @Spy
    ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private GenreService underTest;

    private static final Long ID = 1L;
    private static final String GENRE_NAME = "Genre";

    @BeforeEach
    void beforeEach() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }


    @Test
    void saveOne_savesGenre() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        genreDTO.setName(GENRE_NAME);
        GenreEntity genreEntity = modelMapper.map(genreDTO, GenreEntity.class);
        when(genreRepository.save(any())).thenReturn(genreEntity);
        when(genreRepository.existsByName(genreDTO.getName())).thenReturn(false);
        //when
        GenreDTO savedGenreDTO = underTest.saveOne(genreDTO);
        //then
        assertNotNull(savedGenreDTO);
        assertEquals(genreDTO.getName(), savedGenreDTO.getName());
    }

    @Test
    void saveOne_throwsException_whenGenreNameIsExists() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        genreDTO.setName(GENRE_NAME);
        when(genreRepository.existsByName(genreDTO.getName())).thenReturn(true);
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(genreDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.GENRE_IS_ALREADY_EXIST.getText(genreDTO.getName()), exception.getMessage());
    }

    @Test
    void findOne_throwsException_whenGenreNotFound() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        when(genreRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findOne(ID));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.GENRE_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void findOne_returnsGenreDTO() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        genreDTO.setId(ID);
        genreDTO.setName(GENRE_NAME);
        GenreEntity genreEntity = modelMapper.map(genreDTO, GenreEntity.class);
        when(genreRepository.findById(ID)).thenReturn(Optional.of(genreEntity));
        //when
        GenreDTO existingGenre = underTest.findOne(ID);
        //then
        assertNotNull(existingGenre);
        assertEquals(genreDTO.getId(), existingGenre.getId());
        assertEquals(genreDTO.getName(), existingGenre.getName());
    }

    @Test
    void updateOne_updatesGenre() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        genreDTO.setName(GENRE_NAME);
        GenreEntity genreEntity = modelMapper.map(genreDTO, GenreEntity.class);
        GenreEntity existingGenreEntity = Instancio.create(GenreEntity.class);
        when(genreRepository.findById(ID)).thenReturn(Optional.of(existingGenreEntity));
        when(genreRepository.save(any())).thenReturn(genreEntity);
        //when
        GenreDTO updatedGenreDTO = underTest.updateOne(ID, genreDTO);
        //then

        assertEquals(genreDTO.getName(), updatedGenreDTO.getName());
    }

    @Test
    void updateOne_throwsException_whenGenreNotFound() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        when(genreRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.updateOne(ID, genreDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.GENRE_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void updateOne_throwsException_whenGenreNameExists() throws CustomException {
        //given
        GenreDTO genreDTO = Instancio.create(GenreDTO.class);
        genreDTO.setName(GENRE_NAME);
        when(genreRepository.existsByName(genreDTO.getName())).thenReturn(true);
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.updateOne(ID, genreDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.GENRE_IS_ALREADY_EXIST.getText(genreDTO.getName()), exception.getMessage());
    }

    @Test
    void deleteOne_throwsException_whenGenreNotFound() throws CustomException {
        //given
        when(genreRepository.findById(ID)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deleteOne(ID));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(MessageSource.GENRE_NOT_FOUND.getText(ID.toString()), exception.getMessage());
    }

    @Test
    void deleteOne_deletesGenre() throws CustomException {
        //given
        ArgumentCaptor<GenreEntity> genreEntityArgumentCaptor = ArgumentCaptor.forClass(GenreEntity.class);
        GenreEntity genreEntity = Instancio.create(GenreEntity.class);
        genreEntity.setId(ID);
        when(genreRepository.findById(ID)).thenReturn(Optional.of(genreEntity));
        //when
        underTest.deleteOne(ID);
        //then
        verify(genreRepository).delete(genreEntityArgumentCaptor.capture());
        GenreEntity deletedGenre = genreEntityArgumentCaptor.getValue();
        assertEquals(genreEntity, deletedGenre);
    }
}