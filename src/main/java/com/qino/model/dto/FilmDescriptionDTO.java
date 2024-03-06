package com.qino.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilmDescriptionDTO extends BaseDTO {
    private String description;

}
