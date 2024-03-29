package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.GenreDTO;
import com.qino.model.entity.GenreEntity;
import com.qino.repository.GenreRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {
    private GenreRepository genreRepository;
    private ModelMapper modelMapper;


    /**
     * @param genreDTO genre to save
     * @return saved genre
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public GenreDTO saveOne(GenreDTO genreDTO) throws CustomException {
        String genreName = capitalize(genreDTO.getName().trim());
        existByName(genreName);
        genreDTO.setName(genreName);
        GenreEntity genreEntity = genreRepository.save(modelMapper.map(genreDTO, GenreEntity.class));

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    public void saveAll(Set<String> genres) throws CustomException {
        for (String genre : genres) {
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setName(genre);
            saveOne(genreDTO);
        }

    }

    /**
     * @param id genre id
     * @return existing genre
     * @throws CustomException if any validation fails
     */
    public GenreDTO findOne(Long id) throws CustomException {
        GenreEntity genreEntity = findById(id);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    public Set<GenreDTO> getAllGenres() {
        return genreRepository.findAll()
            .stream().sorted(Comparator.comparing(GenreEntity::getId))
            .map(GenreDTO::new)
            .collect(Collectors.toSet());
    }

    /**
     * @param id       genre id
     * @param genreDTO data to update
     * @return updated genre
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public GenreDTO updateOne(Long id, GenreDTO genreDTO) throws CustomException {
        String genreName = capitalize(genreDTO.getName().trim());
        existByName(genreName);
        GenreEntity genreEntity = findById(id);
        genreEntity.setName(genreName);

        return modelMapper.map(genreRepository.save(genreEntity), GenreDTO.class);
    }

    /**
     * @param id genre id
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public void deleteOne(Long id) throws CustomException {
        GenreEntity genreEntity = findById(id);

        genreRepository.delete(genreEntity);
    }

    @Transactional
    public Set<GenreEntity> getAllEntitiesByIds(Set<Long> ids) throws CustomException {
        if (ids == null) {
            return Collections.emptySet();
        }

        Set<GenreEntity> genreEntitySet = genreRepository.findAllByIdIn(ids);

        if (genreEntitySet.size() < ids.size()) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_NOT_FOUND.getText(Arrays.toString(ids.toArray())))
                .build();
        }

        return genreEntitySet;
    }

    /**
     * @param id genre id
     * @return existing genre
     * @throws CustomException if any validation fails
     */
    private GenreEntity findById(Long id) throws CustomException {
        return genreRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.GENRE_NOT_FOUND.getText(id.toString()))
                .build());
    }


    /**
     * @param name genre's name
     * @throws CustomException if any validation fails
     */
    private void existByName(String name) throws CustomException {
        if (genreRepository.existsByName(name)) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_IS_ALREADY_EXIST.getText(name))
                .build();
        }
    }

    /**
     * @param name genre's name validate
     * @return capitalized genre's name
     */
    private String capitalize(String name) {
        String[] words = name.split(" ");
        StringBuilder capitalizedGenre = new StringBuilder();

        for (String word : words) {
            word = word.toLowerCase();
            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            capitalizedGenre.append(cap).append(" ");
        }

        return capitalizedGenre.toString().trim();
    }
}
