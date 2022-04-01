package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingListDto;
import com.nnk.springboot.dto.rating.RatingUpdateDto;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Save a rating
     * @param rating rating information
     * @return rating
     */
    @Transactional
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Get all rating list
     * @return all rating list
     */
    public List<RatingListDto> getAllRatingList() {
        List<RatingListDto> dto = new ArrayList<>();
        ratingRepository.findAll().forEach(rating ->
                dto.add(RatingListDto.builder()
                        .id(rating.getId())
                        .orderNumber(rating.getOrderNumber())
                        .moodysRating(rating.getMoodysRating())
                        .sandPRating(rating.getSandPRating())
                        .fitchRating(rating.getFitchRating())
                        .build()));
        return dto;
    }

    /**
     * Get a rating information before update
     * @param id rating id
     * @return rating information
     */
    @Transactional
    public RatingUpdateDto getRatingUpdateFormData(Integer id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return RatingUpdateDto.builder()
                .orderNumber(rating.getOrderNumber())
                .moodysRating(rating.getMoodysRating())
                .sandPRating(rating.getSandPRating())
                .fitchRating(rating.getFitchRating())
                .build();
    }

    /**
     * Update a rating
     * @param id rating id
     * @param dto rating information
     */
    @Transactional
    public void update(Integer id, RatingUpdateDto dto) {
        Rating rating = ratingRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        rating.setOrderNumber(dto.getOrderNumber());
        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
    }

    /**
     * Delete rating
     * @param id rating id
     * @throws RuntimeException id not found in DB
     */
    public void delete(Integer id) throws RuntimeException {
        ratingRepository.deleteById(id);
    }
}
