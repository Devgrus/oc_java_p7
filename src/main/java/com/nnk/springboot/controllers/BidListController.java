package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidAddDto;
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

import javax.validation.Valid;


@Controller
@RequestMapping("/bidList")
public class BidListController {

    private final BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(BidAddDto bidAddDto) {
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(@Validated BidAddDto bidAddDto, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if(result.hasErrors()) {
            return "bidList/add";
        }

        BidList bidList = BidList.builder()
                .account(bidAddDto.getAccount())
                .type(bidAddDto.getType())
                .bidQuantity(bidAddDto.getBidQuantity().doubleValue()) // Convert to double
                .build();

        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}