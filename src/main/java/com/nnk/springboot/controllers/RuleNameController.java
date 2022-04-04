package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.ruleName.RuleNameAddDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Rule name list page
     * @param model rule name list
     * @return rule name list page
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNameList", ruleNameService.getAllRuleNameList());
        return "ruleName/list";
    }

    /**
     * Add rule name page
     * @param ruleName new rule name information
     * @return add rule name page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(@ModelAttribute("ruleName") RuleNameAddDto ruleName) {
        return "ruleName/add";
    }

    /**
     * Validate a new rule name information
     * @param ruleName rule name information
     * @param result result of validation
     * @param model if result is false, it will have ruleNameAddDto and error message of validation
     * @return Add rule name page or rule name list page
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Validated @ModelAttribute("ruleName") RuleNameAddDto ruleName, BindingResult result, Model model) {
        if(result.hasErrors()) return "ruleName/add";
        ruleNameService.save(ruleName.toEntity());
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
            model.addAttribute("ruleName", ruleNameService.getRuleNameUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    /**
     * Update rule name information
     * @param id rule name id
     * @param ruleName rule name information
     * @param result result of validation
     * @param model if result is false, it will have ruleNameUpdateDto and error message of validation
     * @return update rule name page or rule name list page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Validated @ModelAttribute("ruleName") RuleNameUpdateDto ruleName,
                                 BindingResult result, Model model) {
        if(result.hasErrors()) return "ruleName/update";
        try {
            ruleNameService.update(id, ruleName);
        } catch (NoSuchElementException e) {
            return "redirect:/ruleName/list";
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Delete a rule name
     * @param id rule name id
     * @return rule name list page
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}