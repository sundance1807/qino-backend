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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {
    private GenreRepository genreRepository;
    private ModelMapper modelMapper;


    /**
     *
     * @param genreDTO genre to create;
     * @return  created genre
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public GenreDTO saveOne(GenreDTO genreDTO) throws CustomException {
        if (genreRepository.existsByName(genreDTO.getName().trim())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_IS_ALREADY_EXIST.getText(String.valueOf(genreDTO.getName())))
                .build();
        }

        genreDTO.setName(validateGenreName(genreDTO.getName()));
        GenreEntity genreEntity = modelMapper.map(genreDTO, GenreEntity.class);
        genreRepository.save(genreEntity);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    /**
     *
     * @param genreDTOSet set of genres to save
     * @return set of saved genres
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public Set<GenreDTO> saveAll(Set<GenreDTO> genreDTOSet) throws CustomException {
        Set<GenreDTO> savedGenres = new HashSet<>();
        for (GenreDTO genreDTO : genreDTOSet) {
            GenreDTO savedGenre = saveOne(genreDTO);
            savedGenres.add(savedGenre);
        }
        return savedGenres;
    }

    /**
     *
     * @param id genre id
     * @return existing genre
     * @throws CustomException if any validation fails
     */

    public GenreDTO findOne(Long id) throws CustomException {
        GenreEntity genreEntity = findById(id);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    /**
     *
     * @param id genre id
     * @param genreDTO genre dto
     * @return updated genre
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public GenreDTO updateOne(Long id, GenreDTO genreDTO) throws CustomException {
        GenreEntity genreEntity = findById(id);
        genreEntity.setName(genreDTO.getName().trim());
        genreEntity = genreRepository.save(genreEntity);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    /**
     *
     * @param id genre id
     * @throws CustomException if any validation fails
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public void deleteOne(Long id) throws CustomException {
        GenreEntity genreEntity = findById(id);

        genreRepository.delete(genreEntity);
    }


    /**
     *
     * @return set of existing genres
     */
    public Set<GenreDTO> findAll() {
        return genreRepository.findAll().stream()
            .map(GenreDTO::new)
            .collect(Collectors.toSet());
    }

    /**
     *
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
     *
     * @param genreName genre name to capitalize
     * @return capitalized genre name
     */
    private String validateGenreName(String genreName) {
        String[] words = genreName.split(" ");
        StringBuilder capitalizedGenre = new StringBuilder();

        for (String word : words) {
            word = word.toLowerCase();
            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            capitalizedGenre.append(cap).append(" ");
        }
        return capitalizedGenre.toString().trim();
    }
}
