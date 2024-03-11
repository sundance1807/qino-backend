package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDTO;
import com.qino.model.dto.FilmPreviewDTO;
import com.qino.model.entity.FilmEntity;
import com.qino.model.entity.GenreEntity;
import com.qino.model.entity.PersonEntity;
import com.qino.repository.FilmRepository;
import com.qino.repository.GenreRepository;
import com.qino.repository.PersonRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmService {
    private FilmRepository filmRepository;
    private PersonRepository personRepository;
    private GenreRepository genreRepository;
    private ModelMapper modelMapper;

    /**
     * @param filmDTO film to save
     * @return saved film
     * @throws CustomException if releaseYear out of bound
     */
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, CustomException.class})
    public FilmDTO saveOne(FilmDTO filmDTO) throws CustomException {
        FilmEntity filmEntity = modelMapper.map(filmDTO, FilmEntity.class);
        filmEntity.setTitle(filmEntity.getTitle().trim());
        filmEntity.setReleaseYear(validateReleaseYear(filmEntity.getReleaseYear()));
        filmEntity.setGenres(validateGenres(filmEntity.getGenres()));
        filmEntity.setDirectors(validatePersons(filmEntity.getDirectors()));
        filmEntity.setProducers(validatePersons(filmEntity.getProducers()));
        filmEntity.setWriters(validatePersons(filmEntity.getWriters()));
        filmEntity.setComposers(validatePersons(filmEntity.getComposers()));
        filmEntity.setActors(validatePersons(filmEntity.getActors()));
        filmEntity = filmRepository.save(filmEntity);

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    /**
     *
     * @param id film id
     * @return existing film
     * @throws CustomException if film not found
     */
    public FilmDTO getOne(Long id) throws CustomException {
        FilmEntity filmEntity = findById(id);
        filmEntity.setRating(filmRepository.getRating(id));
        filmEntity.setVotes(filmRepository.getVotes(id));

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    /**
     *
     * @return set of film previews
     */
    public Set<FilmPreviewDTO> getAllFilmPreviews() {
        Comparator<FilmPreviewDTO> filmComparator = Comparator.comparing(FilmPreviewDTO::getId);
        Set<FilmPreviewDTO> sortedFilmPreviews = new TreeSet<>(filmComparator);
        sortedFilmPreviews.addAll(filmRepository.findAll()
            .stream()
            .map(FilmPreviewDTO::new)
            .collect(Collectors.toSet()));

        return sortedFilmPreviews;
    }


    /**
     * @param id      film id
     * @param filmDTO film data to update
     * @return updated film
     * @throws CustomException if film not found
     */
    public FilmDTO updateOne(Long id, FilmDTO filmDTO) throws CustomException {
        FilmEntity filmEntity = modelMapper.map(filmDTO, FilmEntity.class);
        filmEntity.setId(id);
        filmEntity.setTitle(filmEntity.getTitle().trim());
        filmEntity.setReleaseYear(validateReleaseYear(filmEntity.getReleaseYear()));
        filmEntity.setGenres(validateGenres(filmEntity.getGenres()));
        filmEntity.setDirectors(validatePersons(filmEntity.getDirectors()));
        filmEntity.setProducers(validatePersons(filmEntity.getProducers()));
        filmEntity.setWriters(validatePersons(filmEntity.getWriters()));
        filmEntity.setComposers(validatePersons(filmEntity.getComposers()));
        filmEntity.setActors(validatePersons(filmEntity.getActors()));
        filmEntity = filmRepository.save(filmEntity);

        return modelMapper.map(filmEntity, FilmDTO.class);
    }

    /**
     * @param id film id
     * @throws CustomException if film not found
     */
    public void deleteOne(Long id) throws CustomException {
        FilmEntity filmEntity = findById(id);
        filmRepository.delete(filmEntity);
    }

    /**
     *
     * @param id film id
     * @return existing film
     * @throws CustomException if film not found
     */
    private FilmEntity findById(Long id) throws CustomException {
        return filmRepository.findById(id).orElseThrow(() -> CustomException.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(MessageSource.FILM_NOT_FOUND.getText(id.toString()))
            .build());
    }

    /**
     *
     * @param year release year
     * @return year
     * @throws CustomException if year out of bound
     */
    private Year validateReleaseYear(Year year) throws CustomException {
        if (year.isAfter(Year.now())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.RELEASE_YEAR_OUT_OF_BOUND.getText(year.toString()))
                .build();
        }

        return year;
    }

    /**
     *
     * @param genreEntitySet set of genres id
     * @return set of existing genres
     * @throws CustomException if genre not found
     */
    private Set<GenreEntity> validateGenres(Set<GenreEntity> genreEntitySet) throws CustomException {
        Set<GenreEntity> genres = new HashSet<>();
        for (GenreEntity genreEntity : genreEntitySet) {
            Long id = genreEntity.getId();
            genreEntity = genreRepository.findById(id).orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.GENRE_NOT_FOUND.getText(id.toString()))
                .build());
            genres.add(genreEntity);
        }

        return genres;
    }

    /**
     *
     * @param personEntitySet of persons id
     * @return set of existing persons
     * @throws CustomException if persons not found
     */
    private Set<PersonEntity> validatePersons(Set<PersonEntity> personEntitySet) throws CustomException {
        Set<PersonEntity> persons = new HashSet<>();
        for (PersonEntity personEntity : personEntitySet) {
            Long id = personEntity.getId();
            personEntity = personRepository.findById(id).orElseThrow(() -> CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(MessageSource.PERSON_NOT_FOUND.getText(id.toString()))
                .build());
            persons.add(personEntity);
        }

        return persons;
    }


}