package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingAddDto;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        return "rating/list";
    }

    /**
     * Add rating page
     * @param ratingAddDto new rating information
     * @return add rating page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(RatingAddDto ratingAddDto) {
        return "rating/add";
    }

    /**
     * Validate a new rating information
     * @param ratingAddDto rating information
     * @param result result of validation
     * @param model if result is false, it will have ratingAddDto and error message of validation
     * @return Add rating page or rating list page
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingAddDto ratingAddDto, BindingResult result, Model model) {
        if(result.hasErrors()) return "/rating/add";
        ratingService.save(ratingAddDto.toEntity());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
}