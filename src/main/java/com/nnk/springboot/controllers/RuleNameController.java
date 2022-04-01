package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameAddDto;
import com.nnk.springboot.services.RuleNameService;
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
    public String validate(@Valid RuleNameAddDto ruleNameAddDto, BindingResult result, Model model) {
        if(result.hasErrors()) return "ruleName/add";
        ruleNameService.save(ruleNameAddDto.toEntity());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
}