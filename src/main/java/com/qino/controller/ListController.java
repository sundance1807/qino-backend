package com.qino.controller;

import com.qino.model.dto.FilmPreviewDTO;
import com.qino.model.dto.GenreDTO;
import com.qino.model.dto.PersonDTO;
import com.qino.service.FilmService;
import com.qino.service.GenreService;
import com.qino.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/lists")
public class ListController {
    private FilmService filmService;
    private GenreService genreService;
    private PersonService personService;

    @GetMapping("/films")
    @ResponseStatus(HttpStatus.FOUND)
    public Set<FilmPreviewDTO> getAllFilmPreviews() {
        log.info("Incoming request to get list of films");
        return filmService.getAllFilmPreviews();
    }

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.FOUND)
    public Set<GenreDTO> getAllGenres() {
        log.info("Incoming request to get list of genres");
        return genreService.getAllGenres();
    }

    @GetMapping("/persons")
    @ResponseStatus(HttpStatus.FOUND)
    public Set<PersonDTO> getAllPersons() {
        log.info("Incoming request to get list of persons");
        return personService.getAllPersons();
    }
}
