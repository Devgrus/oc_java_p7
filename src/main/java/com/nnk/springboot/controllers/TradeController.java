package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeAddDto;
import com.nnk.springboot.dto.trade.TradeUpdateDto;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Trade list page
     * @param model trade list
     * @return trade list page
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("tradeList", tradeService.getAllTradeList());
        return "trade/list";
    }

    /**
     * Add trade page
     * @param trade new trade information
     * @return add trade page
     */
    @GetMapping("/trade/add")
    public String addTradeForm(@ModelAttribute("trade") TradeAddDto trade) {
        return "trade/add";
    }

    /**
     * Validate a new trade information
     * @param trade trade information
     * @param result result of validation
     * @param model if result is false, it will have trade and error message of validation
     * @return add trade page or trade list page
     */
    @PostMapping("/trade/validate")
    public String validate(@Validated @ModelAttribute("trade") TradeAddDto trade, BindingResult result, Model model) {
        if(result.hasErrors()) return "trade/add";
        tradeService.save(trade.toEntity());
        return "redirect:/trade/list";
    }

    /**
     * Update trade page
     * @param id trade id
     * @param model id and trade information
     * @return update trade page or trade list page
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id);
            model.addAttribute("trade", tradeService.getTradeUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    /**
     * Update trade information
     * @param id trade id
     * @param trade trade information
     * @param result result of validation
     * @param model if result is false, it will have trade and error message of validation
     * @return update trade page or trade list page
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Validated @ModelAttribute("trade")TradeUpdateDto trade,
                              BindingResult result, Model model) {
        if(result.hasErrors()) return "trade/update";
        try {
            tradeService.update(id, trade);
        } catch (NoSuchElementException e) {
            return "redirect:/trade/list";
        }
        return "redirect:/trade/list";
    }

    /**
     * Delete a trade
     * @param id trade id
     * @return trade list page
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}