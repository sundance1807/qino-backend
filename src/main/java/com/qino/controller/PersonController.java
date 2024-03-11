package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.PersonDTO;
import com.qino.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {
    private PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO saveOne(@RequestBody PersonDTO personDTO) throws CustomException {
        log.info("Incoming request to save person: {}", personDTO);

        return personService.saveOne(personDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonDTO findOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to find person with id: {}", id);

        return personService.findOne(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Set<PersonDTO> findAll() {
        log.info("Incoming request to find all persons");

        return personService.getAllPersons();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO updateOne(@PathVariable("id") Long id, @RequestBody PersonDTO personDTO) throws CustomException {
        log.info("Incoming request to update person with id: {}", id);

        return personService.updateOne(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to delete person with id: {}", id);
        personService.deleteOne(id);
    }

    @PostMapping("/generate/{total}")
    @ResponseStatus(HttpStatus.OK)
    public void generate(@PathVariable("total") int total) {
        personService.generate(total);
    }
}
