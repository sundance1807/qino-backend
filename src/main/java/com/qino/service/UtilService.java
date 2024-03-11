package com.qino.service;

import com.qino.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class UtilService {
    private UserService userService;
    private GenreService genreService;
    private PersonService personService;

    public void generate(Integer total, Set<String> genres) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            try {
                userService.generate(total);
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        });
        executor.submit(() -> personService.generate(total));
        executor.submit(() -> {
            try {
                genreService.saveAll(genres);
            } catch (CustomException e) {
                throw new RuntimeException(e);
            }
        });
        executor.shutdown();
    }
}
