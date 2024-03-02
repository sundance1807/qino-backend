package com.qino.model.dto;

import com.qino.model.entity.GenreEntity;
import com.qino.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    private Long id;
    private String name;

    public GenreDTO(GenreEntity genreEntity) {
        this.name = genreEntity.getName();
    }
}
