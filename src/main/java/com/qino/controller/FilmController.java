package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDTO;
import com.qino.model.dto.FilmDescriptionDTO;
import com.qino.service.FilmDescriptionService;
import com.qino.service.FilmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/films")
@AllArgsConstructor
@Slf4j
public class FilmController {
    private FilmService filmService;
    private FilmDescriptionService filmDescriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO saveOne(@RequestBody FilmDTO filmDTO) throws CustomException {
        log.info("Incoming request to create film with title: {}.", filmDTO.getTitle());
        return filmService.saveOne(filmDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO findOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to get film with id: {}.", id);
        return filmService.findOne(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmDTO updateOne(@PathVariable("id") Long id, @RequestBody FilmDTO filmDTO) throws CustomException {
        log.info("Incoming request to update film with id: {}.", id);
        return filmService.updateOne(id, filmDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to delete film with id: {}.", id);
        filmService.deleteOne(id);
    }

    @PostMapping("/{id}/description")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDescriptionDTO setDescription(@PathVariable("id") Long id,
                                             @RequestBody FilmDescriptionDTO filmDescriptionDTO) {
        log.info("Incoming request to save description for film with id: {}", id);
        return filmDescriptionService.setDescription(id, filmDescriptionDTO);
    }

    @GetMapping("/{id}/description")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDescriptionDTO setDescription(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to get description for film with id: {}", id);
        return filmDescriptionService.getDescription(id);
    }
}