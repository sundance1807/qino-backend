package com.qino.controller;

import com.qino.exception.CustomException;
import com.qino.model.dto.FilmDTO;
import com.qino.model.dto.ReviewDTO;
import com.qino.service.FilmService;
import com.qino.service.ReviewService;
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
    private ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO saveOne(@RequestBody FilmDTO filmDTO) throws CustomException {
        log.info("Incoming request to create film with title: {}.", filmDTO.getTitle());
        return filmService.saveOne(filmDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO getOne(@PathVariable("id") Long id) throws CustomException {
        log.info("Incoming request to get film with id: {}.", id);
        return filmService.getOne(id);
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

    @PostMapping("/{filmId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO saveReview(@PathVariable("filmId") Long filmId,
                                @RequestBody ReviewDTO reviewDTO) throws CustomException {
        log.info("Incoming request to save review for film with id: {}", filmId);

        return reviewService.saveOne(filmId, reviewDTO);
    }

    @GetMapping("/{filmId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ReviewDTO getReview(@PathVariable("filmId") Long filmId,
                               @PathVariable("reviewId") Long reviewId) throws CustomException {
        log.info("Incoming request to get review with id: {}", filmId);

        return reviewService.getOne(filmId, reviewId);
    }

    @PutMapping("/{filmId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO updateReview(@PathVariable("filmId") Long filmId,
                                  @PathVariable("reviewId") Long reviewId,
                                  @RequestBody ReviewDTO reviewDTO) throws CustomException {
        log.info("Incoming request to update review with id: {}", reviewId);

        return reviewService.updateOne(filmId, reviewId, reviewDTO);
    }

    @DeleteMapping("/{filmId}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("filmId") Long filmId,
                             @PathVariable("reviewId") Long reviewId) throws CustomException {
        log.info("Incoming request to update review with id: {}", reviewId);
        reviewService.deleteOne(filmId, reviewId);
    }


}
