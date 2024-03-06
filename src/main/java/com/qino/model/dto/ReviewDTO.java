package com.qino.model.dto;

import com.qino.model.ReviewType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewDTO extends BaseDTO {
    @NonNull
    private Long userId;
    @NonNull
    private ReviewType type;
    @NonNull
    private Short rate;
    private String title;
    private String description;
}
