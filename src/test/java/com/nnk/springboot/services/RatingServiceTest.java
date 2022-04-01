package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingUpdateDto;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingService ratingService;

    @Test
    public void getRatingUpdateFormTest() {
        //given
        Rating rating = Rating.builder()
                .id(1).orderNumber(2).moodysRating("A").sandPRating("A").fitchRating("A")
                .build();

        //when
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));

        //then
        assertThat(ratingService.getRatingUpdateFormData(rating.getId()).getOrderNumber()).isEqualTo(rating.getOrderNumber());
        assertThat(ratingService.getRatingUpdateFormData(rating.getId()).getMoodysRating()).isEqualTo(rating.getMoodysRating());
        assertThat(ratingService.getRatingUpdateFormData(rating.getId()).getSandPRating()).isEqualTo(rating.getSandPRating());
        assertThat(ratingService.getRatingUpdateFormData(rating.getId()).getFitchRating()).isEqualTo(rating.getFitchRating());
    }

    @Test
    public void getRatingUpdateFormTestIdNotFound() {
        //given
        Integer id = 1;

        //when
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> ratingService.getRatingUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() {
        //given
        Rating rating = Rating.builder()
                .id(1).orderNumber(2).moodysRating("A").sandPRating("A").fitchRating("A")
                .build();

        RatingUpdateDto dto = RatingUpdateDto.builder()
                .orderNumber(3).moodysRating("B").sandPRating("B").fitchRating("B")
                .build();

        //when
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        ratingService.update(rating.getId(), dto);

        //then
        assertThat(rating.getOrderNumber()).isEqualTo(dto.getOrderNumber());
        assertThat(rating.getMoodysRating()).isEqualTo(dto.getMoodysRating());
        assertThat(rating.getSandPRating()).isEqualTo(dto.getSandPRating());
        assertThat(rating.getFitchRating()).isEqualTo(dto.getFitchRating());

    }

    @Test
    public void updateTestIdNotFound() {
        //given
        Integer id = 1;

        RatingUpdateDto dto = RatingUpdateDto.builder()
                .orderNumber(3).moodysRating("B").sandPRating("B").fitchRating("B")
                .build();

        //when
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> ratingService.update(id, dto)).hasMessage("ID NOT FOUND");
    }
}
