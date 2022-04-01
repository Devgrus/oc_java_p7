package com.nnk.springboot.dto.rating;

import lombok.*;

@Data
@Builder
public class RatingListDto {
    private Integer id;
    private Integer orderNumber;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
}
