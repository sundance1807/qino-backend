package com.qino.service;

import com.qino.exception.CustomException;
import com.qino.model.dto.ReviewDTO;
import com.qino.model.entity.FilmEntity;
import com.qino.model.entity.ReviewEntity;
import com.qino.model.entity.UserEntity;
import com.qino.repository.FilmRepository;
import com.qino.repository.ReviewRepository;
import com.qino.repository.UserRepository;
import com.qino.util.MessageSource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    private FilmRepository filmRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private ModelMapper modelMapper;

    /**
     * @param filmId    film id
     * @param reviewDTO review to save
     * @return saved review
     * @throws CustomException if film or user not found
     */
    public ReviewDTO saveOne(Long filmId, ReviewDTO reviewDTO) throws CustomException {
        ReviewEntity reviewEntity = modelMapper.map(reviewDTO, ReviewEntity.class);
        FilmEntity filmEntity = getFilmEntityById(filmId);
        UserEntity userEntity = getUserEntityById(reviewDTO.getUserId());
        reviewEntity.setFilm(filmEntity);
        reviewEntity.setUser(userEntity);
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setDescription(reviewDTO.getDescription());
        reviewEntity = reviewRepository.save(reviewEntity);

        return modelMapper.map(reviewEntity, ReviewDTO.class);
    }

    /**
     * @param filmId   film id
     * @param reviewId review id
     * @return existing review
     * @throws CustomException if film or review not found
     */
    public ReviewDTO getOne(Long filmId, Long reviewId) throws CustomException {
        getFilmEntityById(filmId);
        ReviewEntity reviewEntity = findReviewEntityById(reviewId);

        return modelMapper.map(reviewEntity, ReviewDTO.class);
    }

    /**
     * @param filmId    film id
     * @param reviewId  review id
     * @param reviewDTO review data to update
     * @return updated review
     * @throws CustomException if review or film or user not found
     */
    public ReviewDTO updateOne(Long filmId, Long reviewId, ReviewDTO reviewDTO) throws CustomException {
        ReviewEntity reviewEntity = findReviewEntityById(reviewId);
        FilmEntity filmEntity = getFilmEntityById(filmId);
        UserEntity userEntity = getUserEntityById(reviewDTO.getUserId());

        if (!reviewEntity.getUser().getId().equals(userEntity.getId())) {
            throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.UNABLE_TO_UPDATE_BY_ANOTHER_USER.getText())
                .build();
        }

        reviewEntity.setUser(userEntity);
        reviewEntity.setFilm(filmEntity);
        reviewEntity.setType(reviewDTO.getType());
        reviewEntity.setRate(reviewDTO.getRate());
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setDescription(reviewDTO.getDescription());
        reviewEntity = reviewRepository.save(reviewEntity);

        return modelMapper.map(reviewEntity, ReviewDTO.class);
    }

    /**
     * @param filmId   film id
     * @param reviewId review id
     * @throws CustomException if film or review not found
     */
    public void deleteOne(Long filmId, Long reviewId) throws CustomException {
        getFilmEntityById(filmId);
        ReviewEntity reviewEntity = findReviewEntityById(reviewId);

        reviewRepository.delete(reviewEntity);
    }

    /**
     * @param id film id
     * @return filmEntity
     * @throws CustomException if film not found
     */
    private FilmEntity getFilmEntityById(Long id) throws CustomException {
        return filmRepository.findById(id).orElseThrow(() -> CustomException.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(MessageSource.FILM_NOT_FOUND.getText(id.toString()))
            .build());
    }

    /**
     * @param id user id
     * @return userEntity
     * @throws CustomException if user not found
     */
    private UserEntity getUserEntityById(Long id) throws CustomException {
        return userRepository.findById(id).orElseThrow(() -> CustomException.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(MessageSource.USER_NOT_FOUND.getText(id.toString()))
            .build());
    }

    /**
     * @param id review id
     * @return reviewEntity
     * @throws CustomException if review not found
     */
    private ReviewEntity findReviewEntityById(Long id) throws CustomException {
        return reviewRepository.findById(id).orElseThrow(() -> CustomException.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(MessageSource.REVIEW_NOT_FOUND.getText(id.toString()))
            .build());
    }
}
