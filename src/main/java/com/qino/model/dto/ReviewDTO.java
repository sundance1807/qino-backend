package com.qino.model.dto;

import com.qino.model.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    @NonNull
    private Long userId;
    @NonNull
    private ReviewType type;
    @NonNull
    private Short rate;
    private String title;
    private String description;
}
