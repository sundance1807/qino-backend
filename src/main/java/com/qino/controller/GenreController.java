package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.GenreDTO;
import com.qino.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public GenreDTO findOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to get a genre with id: {}.", id);
        return genreService.findOne(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDTO updateOne(@PathVariable("id") Long id, @RequestBody GenreDTO genreDTO) throws CustomException {
        log.info("Incoming request to update genre with id: {}.", id);
        return genreService.updateOne(id, genreDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to delete a genre with id: {}.", id);
        genreService.deleteOne(id);
    }
}
