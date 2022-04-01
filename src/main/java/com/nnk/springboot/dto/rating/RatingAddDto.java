package com.nnk.springboot.dto.rating;

import com.nnk.springboot.domain.Rating;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
public class RatingAddDto {
    @NotNull(message = "Order number is mandatory")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @Size(max = 125, message = "125 characters maximum")
    private String moodysRating;

    @Size(max = 125, message = "125 characters maximum")
    private String sandPRating;

    @Size(max = 125, message = "125 characters maximum")
    private String fitchRating;

    public Rating toEntity() {
        return Rating.builder()
                .orderNumber(orderNumber)
                .moodysRating(moodysRating)
                .sandPRating(sandPRating)
                .fitchRating(fitchRating)
                .build();
    }
}
