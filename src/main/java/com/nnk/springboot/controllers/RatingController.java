package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingAddDto;
import com.nnk.springboot.dto.rating.RatingUpdateDto;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Rating list page
     * @param model rating list
     * @return rating list page
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratingList", ratingService.getAllRatingList());
        return "rating/list";
    }

    /**
     * Add rating page
     * @param rating new rating information
     * @return add rating page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(@ModelAttribute("rating") RatingAddDto rating) {
        return "rating/add";
    }

    /**
     * Validate a new rating information
     * @param rating rating information
     * @param result result of validation
     * @param model if result is false, it will have ratingAddDto and error message of validation
     * @return Add rating page or rating list page
     */
    @PostMapping("/rating/validate")
    public String validate(@Validated @ModelAttribute("rating") RatingAddDto rating, BindingResult result, Model model) {
        if(result.hasErrors()) return "/rating/add";
        ratingService.save(rating.toEntity());
        return "redirect:/rating/list";
    }

    /**
     * Update rating page
     * @param id rating id
     * @param model id and rating information
     * @return update rating page or rating list page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id.toString());
            model.addAttribute("rating", ratingService.getRatingUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    /**
     * Update rating information
     * @param id rating id
     * @param rating rating information
     * @param result result of validation
     * @param model if result is false, it will have ratingUpdateDto and error message of validation
     * @return Update rating page or rating list page
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Validated @ModelAttribute("rating") RatingUpdateDto rating,
                               BindingResult result, Model model) {
        if(result.hasErrors()) return "rating/update";
        try {
            ratingService.update(id, rating);
        } catch (NoSuchElementException e) {
            return "redirect:/rating/list";
        }
        return "redirect:/rating/list";
    }

    /**
     * Delete a rating
     * @param id rating id
     * @return rating list page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}