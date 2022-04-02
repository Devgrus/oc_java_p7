package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.ruleName.RuleNameAddDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
        return "ruleName/list";
    }

    /**
     * Add rule name page
     * @param ruleNameAddDto new rule name information
     * @return add rule name page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameAddDto ruleNameAddDto) {
        return "ruleName/add";
    }

    /**
     * Validate a new rule name information
     * @param ruleNameAddDto rule name information
     * @param result result of validation
     * @param model if result is false, it will have ruleNameAddDto and error message of validation
     * @return Add rule name page or rule name list page
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Validated RuleNameAddDto ruleNameAddDto, BindingResult result, Model model) {
        if(result.hasErrors()) return "ruleName/add";
        ruleNameService.save(ruleNameAddDto.toEntity());
        return "redirect:/ruleName/list";
    }

    /**
     * Update rule name page
     * @param id rule name id
     * @param model id and rule name information
     * @return update rule name page or rule name list page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id.toString());
            model.addAttribute("ruleNameUpdateDto", ruleNameService.getRuleNameUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    /**
     * Update rule name information
     * @param id rule name id
     * @param ruleNameUpdateDto rule name information
     * @param result result of validation
     * @param model if result is false, it will have ruleNameUpdateDto and error message of validation
     * @return update rule name page or rule name list page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Validated RuleNameUpdateDto ruleNameUpdateDto,
                                 BindingResult result, Model model) {
        if(result.hasErrors()) return "ruleName/update";
        try {
            ruleNameService.update(id, ruleNameUpdateDto);
        } catch (NoSuchElementException e) {
            return "redirect:/ruleName/list";
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
}