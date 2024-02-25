package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.DirectorDTO;
import com.qino.service.DirectorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/directors")
@Slf4j
public class DirectorController {
    private DirectorService directorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DirectorDTO saveOne(@RequestBody DirectorDTO directorDTO) {
        log.info("Incoming request to save a director: {}.", directorDTO.getFullName());
        return directorService.saveOne(directorDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public DirectorDTO getOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to find a director with id: {}.", id);
        return directorService.findOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Set<DirectorDTO> getAll() {
        log.info("Incoming request to find all directors.");
        return directorService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable ("id") Long id) throws CustomException {
        log.info("Incoming request to delete a director with id: {}.", id);
        directorService.deleteOne(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALl() {
        log.info("Incoming request to delete all directors.");
        directorService.deleteAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DirectorDTO updateOne(@PathVariable("id") Long id, DirectorDTO directorDTO) throws CustomException{
        log.info("Incoming request to update a director with id: {}.", id);
        return directorService.updateOne(id, directorDTO);
    }
}
