package com.nnk.springboot.dto.rating;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
public class RatingUpdateDto {
    @NotNull(message = "Order number is mandatory")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    @Size(max = 125, message = "125 characters maximum")
    private String moodysRating;

    @Size(max = 125, message = "125 characters maximum")
    private String sandPRating;

    @Size(max = 125, message = "125 characters maximum")
    private String fitchRating;
}
