package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDetailDTO;
import com.qino.model.entity.FilmDetailEntity;
import com.qino.repository.FilmDetailRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FilmDetailService {
    private FilmDetailRepository filmDetailRepository;
    private ModelMapper modelMapper;

    /**
     *
     * @param id film id
     * @param filmDetailDTO film details data
     * @return setted film details
     * @throws CustomException if film details not found
     */
    public FilmDetailDTO setFilmDetails(Long id, FilmDetailDTO filmDetailDTO) throws CustomException {
        FilmDetailEntity filmDetailEntity =
            filmDetailRepository.findById(id).orElseGet(FilmDetailEntity::new);
        filmDetailEntity.setId(id);
        filmDetailEntity.setDescription(filmDetailDTO.getDescription() == null ?
            filmDetailEntity.getDescription() :
            filmDetailDTO.getDescription());
        filmDetailEntity.setDirectors(filmDetailDTO.getDirectors().isEmpty() ?
            filmDetailEntity.getDirectors() :
            filmDetailDTO.getDirectors());
        filmDetailEntity.setProducers(filmDetailDTO.getProducers().isEmpty() ?
            filmDetailEntity.getProducers() :
            filmDetailDTO.getProducers());
        filmDetailEntity.setWriters(filmDetailDTO.getWriters().isEmpty() ?
            filmDetailEntity.getWriters() :
            filmDetailDTO.getWriters());
        filmDetailEntity.setComposers(filmDetailDTO.getComposers().isEmpty() ?
            filmDetailEntity.getComposers() :
            filmDetailDTO.getComposers());
        filmDetailEntity.setActors(filmDetailDTO.getActors().isEmpty() ?
            filmDetailEntity.getActors() :
            filmDetailDTO.getActors());
        filmDetailEntity.setBudget(filmDetailDTO.getBudget());
        filmDetailEntity.setGrosses(filmDetailDTO.getGrosses());
        filmDetailEntity.setPremiereDate(filmDetailDTO.getPremiereDate());
        filmDetailEntity.setDuration(filmDetailDTO.getDuration());
        filmDetailEntity = filmDetailRepository.save(filmDetailEntity);

        return modelMapper.map(filmDetailEntity, FilmDetailDTO.class);
    }

    /**
     *
     * @param id film id
     * @return film details
     * @throws CustomException if film details not found
     */
    public FilmDetailDTO getFilmDetails(Long id) throws CustomException {
        FilmDetailEntity filmDetailEntity =
            filmDetailRepository.findById(id).orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.FILM_DETAILS_NOT_FOUND.getText())
                .build());

        return modelMapper.map(filmDetailEntity, FilmDetailDTO.class);
    }
}
