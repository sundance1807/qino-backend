package com.qino.controller;

import com.qino.service.UtilService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/utils")
public class UtilController {
    private UtilService utilService;

    @PostMapping("/generate/{total}")
    @ResponseStatus(HttpStatus.OK)
    public void generate(@PathVariable("total") Integer total, @RequestBody Set<String> genres) {
        log.info("Incoming request to generate users and persons.");
        utilService.generate(total, genres);
    }
}
