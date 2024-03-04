package com.qino.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CareerDTO extends BaseDTO {
    @NonNull
    private String name;
    @NonNull
    private String ruTranslation;
}
