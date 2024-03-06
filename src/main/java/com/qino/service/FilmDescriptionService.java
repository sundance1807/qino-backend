package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDescriptionDTO;
import com.qino.model.entity.FilmDescriptionEntity;
import com.qino.repository.FilmDescriptionRepository;
import com.qino.repository.FilmRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class FilmDescriptionService {
    private final FilmRepository filmRepository;
    private final FilmDescriptionRepository filmDescriptionRepository;
    private final ModelMapper modelMapper;


    /**
     * @param id                 film id = description id
     * @param filmDescriptionDTO film description
     * @return film description
     */
    public FilmDescriptionDTO setDescription(Long id, FilmDescriptionDTO filmDescriptionDTO) {
        FilmDescriptionEntity skuDescription =
            filmDescriptionRepository.findById(id).orElseGet(FilmDescriptionEntity::new);
        skuDescription.setId(id);
        skuDescription.setDescription(filmDescriptionDTO.getDescription() == null ? "" : filmDescriptionDTO.getDescription());
        skuDescription = filmDescriptionRepository.save(skuDescription);

        return modelMapper.map(skuDescription, FilmDescriptionDTO.class);
    }

    /**
     * @param id film id = description id
     * @return film description
     * @throws CustomException if film description not found
     */
    public FilmDescriptionDTO getDescription(Long id) throws CustomException {
        FilmDescriptionEntity filmDescriptionEntity = filmDescriptionRepository.findById(id).orElseThrow(
            () -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.FILM_DESCRIPTION_NOT_FOUND.getText(id.toString()))
                .build());

        return modelMapper.map(filmDescriptionEntity, FilmDescriptionDTO.class);
    }
}
