package com.qino.model.dto;

import com.qino.model.entity.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long id;
    private String name;

    public GenreDTO(GenreEntity genreEntity) {
        this.name = genreEntity.getName();
    }
}
