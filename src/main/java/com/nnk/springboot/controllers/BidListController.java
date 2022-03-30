package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.bid.BidAddDto;
import com.nnk.springboot.dto.bid.BidUpdateDto;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;



@Controller
@RequestMapping("/bidList")
public class BidListController {

    private final BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Bid list page
     * @param model bid list
     * @return Bid list page
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("bidList", bidListService.getAllBidList());
        return "bidList/list";
    }

    /**
     * Add bid page
     * @param bidAddDto new bid information
     * @return Add bid page
     */
    @GetMapping("/add")
    public String addBidForm(BidAddDto bidAddDto) {
        return "bidList/add";
    }

    /**
     * Validate a new bid information
     * @param bidAddDto bid information
     * @param result result of validation
     * @param model if result is false, it will have bidAddDto and error message of validation
     * @return Add bid page or bid list page
     */
    @PostMapping("/validate")
    public String validate(@Validated BidAddDto bidAddDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.save(bidAddDto.toEntity());
        return "redirect:/bidList/list";
    }

    /**
     * Update bid page
     * @param id bidListId
     * @param model id and bid information
     * @return update bid page or bid list page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id.toString());
            model.addAttribute("bidUpdateDto", bidListService.getBidUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/bidList/list";
        }
        return "bidList/update";
    }

    /**
     * Update bid information
     * @param id bidListId
     * @param bidUpdateDto bid information
     * @param result result of validation
     * @param model if result is false, it will have bidUpdateDto and error message of validation
     * @return bid list page or bid update page
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Validated BidUpdateDto bidUpdateDto,
                            BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "/bidList/update";
        }

        try {
            bidListService.update(id, bidUpdateDto);
        } catch (NoSuchElementException e) {
            return "redirect:/bidList/list";
        }

        return "redirect:/bidList/list";
    }

    /**
     * Delete a bid
     * @param id bidListId
     * @return bid list page
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        bidListService.delete(id);
        return "redirect:/bidList/list";
    }
}