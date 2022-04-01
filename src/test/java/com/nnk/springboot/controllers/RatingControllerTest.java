package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingAddDto;
import com.nnk.springboot.dto.rating.RatingUpdateDto;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RatingService ratingService;

    @Test
    public void validateTest() throws Exception {
        //given
        RatingAddDto dto = RatingAddDto.builder()
                .orderNumber(5)
                .moodysRating("A")
                .sandPRating("B")
                .fitchRating(null)
                .build();

        Rating rating = dto.toEntity();

        //when
        when(ratingService.save(any())).thenReturn(rating);

        //then
        mockMvc.perform(post("/rating/validate")
                .param("orderNumber", dto.getOrderNumber().toString())
                .param("moodysRating", dto.getMoodysRating())
                .param("sandPRating", dto.getSandPRating())
                .param("fitchRating", dto.getFitchRating()))
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void validateTestOrderNumberIsNotNumber() throws Exception {
        //given
        RatingAddDto dto = RatingAddDto.builder()
                .orderNumber(5)
                .moodysRating("A")
                .sandPRating("B")
                .fitchRating(null)
                .build();

        Rating rating = dto.toEntity();

        //when
        when(ratingService.save(any())).thenReturn(rating);

        //then
        mockMvc.perform(post("/rating/validate")
                        .param("orderNumber", "aa")
                        .param("moodysRating", dto.getMoodysRating())
                        .param("sandPRating", dto.getSandPRating())
                        .param("fitchRating", dto.getFitchRating()))
                .andExpect(model().attributeErrorCount("ratingAddDto", 1));
    }

    @Test
    public void updateRatingTest() throws Exception {
        //given
        int id = 1;

        RatingUpdateDto ratingUpdateDto = RatingUpdateDto.builder()
                .orderNumber(5).moodysRating("A").sandPRating("A").fitchRating("A")
                .build();


        //when
        doNothing().when(ratingService).update(any(), any());

        //then
        mockMvc.perform(post("/rating/update/" + id)
                .param("orderNumber", ratingUpdateDto.getOrderNumber().toString())
                .param("moodysRating", ratingUpdateDto.getMoodysRating())
                .param("sandPRating", ratingUpdateDto.getSandPRating())
                .param("fitchRating", ratingUpdateDto.getFitchRating()))
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void updateRatingTestOrderNumberIsNull() throws Exception {
        //given
        int id = 1;

        RatingUpdateDto ratingUpdateDto = RatingUpdateDto.builder()
                .orderNumber(null).moodysRating("A").sandPRating("A").fitchRating("A")
                .build();


        //when
        doNothing().when(ratingService).update(any(), any());

        //then
        mockMvc.perform(post("/rating/update/" + id)
                        .param("orderNumber", "")
                        .param("moodysRating", ratingUpdateDto.getMoodysRating())
                        .param("sandPRating", ratingUpdateDto.getSandPRating())
                        .param("fitchRating", ratingUpdateDto.getFitchRating()))
                .andExpect(model().attributeErrorCount("ratingUpdateDto", 1));
    }

}
