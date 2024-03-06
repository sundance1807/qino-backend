package com.qino.repository;

import com.qino.model.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    @Query(nativeQuery = true,
        value = "select avg(rate) as rating from reviews where film_id = :filmId")
    Float getRating(@Param("filmId") Long filmId);

    @Query(nativeQuery = true,
        value = "select count(1) as votes from reviews where film_id = :filmId")
    Long getVotes(@Param("filmId") Long filmId);
}
