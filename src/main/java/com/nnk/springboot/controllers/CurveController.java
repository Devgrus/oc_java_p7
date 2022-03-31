
package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointAddDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateDto;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    private final CurvePointService curvePointService;

    @Autowired
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }

    /**
     * Add curve point page
     * @param curvePointAddDto new curve point information
     * @return Add curve point page
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePointAddDto curvePointAddDto) {
        return "curvePoint/add";
    }

    /**
     * Validate a new curve point information
     * @param curvePointAddDto curve point information
     * @param result result of validation
     * @param model if result is false, it will have curvePointAddDto and error message of validation
     * @return Add curve point page or curve point list page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Validated CurvePointAddDto curvePointAddDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.save(curvePointAddDto.toEntity());
        return "redirect:/curvePoint/list";
    }

    /**
     * Update curve point page
     * @param id curve point id
     * @param model id and curve point information
     * @return update curve point page or curve point list page
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id.toString());
            model.addAttribute("curvePointUpdateDto", curvePointService.getCurvePointUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/update";
    }

    /**
     * Update curve point information
     * @param id curve point id
     * @param curvePointUpdateDto curve point information
     * @param result result of validation
     * @param model if result is false, it will have curvePointUpdateDto and error message of validation
     * @return Update curve point page or curve point list page
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Validated CurvePointUpdateDto curvePointUpdateDto,
                            BindingResult result, Model model) {
        if(result.hasErrors()) return "curvePoint/update";
        try {
            curvePointService.update(id, curvePointUpdateDto);
        } catch (NoSuchElementException e) {
            return "redirect:/curvePoint/list";
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}