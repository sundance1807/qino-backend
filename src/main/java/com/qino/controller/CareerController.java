package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.CareerDTO;
import com.qino.service.CareerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/careers")
public class CareerController {
    private final CareerService careerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CareerDTO saveOne(@RequestBody CareerDTO careerDTO) throws CustomException {
        log.info("Incoming request to save career: {}.", careerDTO);
        return careerService.saveOne(careerDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CareerDTO findOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to find career with id: {}", id);
        return careerService.findOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable("id") Long id) throws  CustomException {
        log.info("Incoming request to delete career with: {}", id);
        careerService.deleteOne(id);
    }
}
