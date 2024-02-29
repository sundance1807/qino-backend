package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.GenreDTO;
import com.qino.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
@Slf4j
public class GenreController {
    private GenreService genreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO saveOne(@RequestBody GenreDTO genreDTO) throws CustomException {
        log.info("Incoming request to create a genre: {}.", genreDTO.getName());
        return genreService.saveOne(genreDTO);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public GenreDTO getOne(@PathVariable ("id") Long id) throws CustomException {
        log.info("Incoming request to get a genre with id: {}.", id);
        return genreService.findOne(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GenreDTO updateOne(@PathVariable ("id") Long id, @RequestBody GenreDTO genreDTO) throws CustomException {
        log.info("Incoming request to update '{}' genre.", genreDTO.getName());
        return genreService.updateOne(id, genreDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable ("id") Long id) throws CustomException {
        log.info("Incoming request to delete a genre with id: {}.",id);
        genreService.deleteOne(id);
    }

    @PostMapping("/createAll")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<GenreDTO> saveAll(@RequestBody Set<GenreDTO> genreDTOSet) throws CustomException {
        log.info("Incoming request to create a genres: {}.", genreDTOSet);
        return genreService.saveAll(genreDTOSet);
    }

    @GetMapping("/showAll")
    public Set<GenreDTO> getAll() {
        log.info("Incoming request to get all genres.");
        return genreService.findAll();
    }
}
