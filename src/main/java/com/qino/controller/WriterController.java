package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.WriterDTO;
import com.qino.service.WriterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/writers")
@AllArgsConstructor
@Slf4j
public class WriterController {
    private WriterService writerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WriterDTO saveOne(@RequestBody WriterDTO writerDTO) {
        log.info("Incoming request to save a writer: {}.", writerDTO.getFullName());
        return writerService.saveOne(writerDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public WriterDTO getOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to find a writer with id: {}.", id);
        return writerService.findOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Set<WriterDTO> getAll() {
        log.info("Incoming request to find all writers.");
        return writerService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to delete a writer with id: {}.", id);
        writerService.deleteOne(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALl() {
        log.info("Incoming request to delete all writers.");
        writerService.deleteAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WriterDTO updateOne(@PathVariable("id") Long id, WriterDTO writerDTO) throws CustomException {
        log.info("Incoming request to update a writer with id: {}.", id);
        return writerService.updateOne(id, writerDTO);
    }

}
