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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {
    GenreRepository genreRepository;
    ModelMapper modelMapper;

    public Set<GenreDTO> saveAll(Set<GenreDTO> genreDTOSet) throws CustomException{
        Set<GenreDTO> savedGenres = new HashSet<>();
        for (GenreDTO genreDTO : genreDTOSet) {
            GenreDTO savedGenre = saveOne(genreDTO);
            savedGenres.add(savedGenre);
        }
        return savedGenres;
    }

    public GenreDTO saveOne(GenreDTO genreDTO) throws CustomException {
        if (genreRepository.existsByName(genreDTO.getName().trim())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_IS_ALREADY_EXIST.getText(String.valueOf(genreDTO.getName())))
                .build();
        }
        String[] words = genreDTO.getName().split(" ");
        StringBuilder capitalizedGenre = new StringBuilder();

        for (String word : words) {
            word = word.toLowerCase();
            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            capitalizedGenre.append(cap).append(" ");
        }

        genreDTO.setName(capitalizedGenre.toString().trim());
        GenreEntity genreEntity = modelMapper.map(genreDTO, GenreEntity.class);
        genreRepository.save(genreEntity);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }



    public Set<GenreDTO> findAll() {
        return genreRepository.findAll()
            .stream()
            .map(GenreDTO::new)
            .collect(Collectors.toSet());
    }

    public GenreDTO findOne(Long id) throws CustomException {
        GenreEntity genreEntity = genreRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_NOT_FOUND.getText(String.valueOf(id)))
                .build());

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    public GenreDTO updateOne(Long id, GenreDTO genreDTO) throws CustomException {
        GenreEntity genreEntity = genreRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_NOT_FOUND.getText(String.valueOf(genreDTO.getName().trim())))
                .build());
        genreEntity.setName(genreDTO.getName().trim());
        genreEntity = genreRepository.save(genreEntity);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }

    public Set<GenreDTO> deleteAll() {
        Set<GenreDTO> genreDTOSet = findAll();
        genreRepository.deleteAll();
        return genreDTOSet;
    }

    public GenreDTO deleteOne(Long id) throws CustomException {
        GenreEntity genreEntity = genreRepository.findById(id)
            .orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.GENRE_NOT_FOUND.getText(String.valueOf(id)))
                .build());
        genreRepository.deleteById(id);

        return modelMapper.map(genreEntity, GenreDTO.class);
    }



}
