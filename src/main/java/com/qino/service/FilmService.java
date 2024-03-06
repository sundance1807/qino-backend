package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDTO;
import com.qino.model.entity.FilmEntity;
import com.qino.repository.FilmRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@AllArgsConstructor
public class FilmService {
    private FilmRepository filmRepository;
    private ModelMapper modelMapper;

    /**
     *
     * @param filmDTO film to save
     * @return saved film
     * @throws CustomException if releaseYear out of bound
     */
    public FilmDTO saveOne(FilmDTO filmDTO) throws CustomException {
        validateReleaseYear(filmDTO.getReleaseYear());
        filmDTO.setTitle(filmDTO.getTitle().trim());
        FilmEntity filmEntity = modelMapper.map(filmDTO, FilmEntity.class);
        filmEntity = filmRepository.save(filmEntity);

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    public FilmDTO findOne(Long id) throws CustomException {
        FilmEntity filmEntity = findById(id);

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    /**
     *
     * @param id film id
     * @param filmDTO film data to update
     * @return updated film
     * @throws CustomException if film not found
     */
    public FilmDTO updateOne(Long id, FilmDTO filmDTO) throws CustomException {
        FilmEntity filmEntity = findById(id);
        filmEntity.setTitle(filmDTO.getTitle() == null
            ? filmEntity.getTitle() : filmDTO.getTitle());
        filmEntity.setReleaseYear(filmDTO.getReleaseYear() == null ?
            filmEntity.getReleaseYear() : filmDTO.getReleaseYear());
        filmRepository.save(filmEntity);

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    /**
     *
     * @param id film id
     * @throws CustomException if film not found
     */
    public void deleteOne(Long id) throws CustomException {
        FilmEntity filmEntity = findById(id);
        filmRepository.delete(filmEntity);
    }

    private FilmEntity findById(Long id) throws CustomException {
        return filmRepository.findById(id).orElseThrow(() -> CustomException.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(MessageSource.FILM_NOT_FOUND.getText(id.toString()))
            .build());
    }

    private void validateReleaseYear(Year year) throws CustomException {
        if (year.isAfter(Year.now())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.RELEASE_YEAR_OUT_OF_BOUND.getText(year.toString()))
                .build();
        }
    }
}