
package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.curvePoint.CurvePointAddDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateDto;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class CurveController {

    private final CurvePointService curvePointService;

    @Autowired
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Curve point list page
     * @param model curve point list
     * @return curve point list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePointList", curvePointService.getAllCurvePointList());
        return "curvePoint/list";
    }

    /**
     * Add curve point page
     * @param curvePoint new curve point information
     * @return Add curve point page
     */
    @GetMapping("/curvePoint/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCurveForm(@ModelAttribute("curvePoint") CurvePointAddDto curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validate a new curve point information
     * @param curvePoint curve point information
     * @param result result of validation
     * @param model if result is false, it will have curvePointAddDto and error message of validation
     * @return Add curve point page or curve point list page
     */
    @PostMapping("/curvePoint/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public String validate(@Validated @ModelAttribute("curvePoint") CurvePointAddDto curvePoint, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.save(curvePoint.toEntity());
        return "redirect:/curvePoint/list";
    }

    /**
     * Update curve point page
     * @param id curve point id
     * @param model id and curve point information
     * @return update curve point page or curve point list page
     */
    @GetMapping("/curvePoint/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id.toString());
            model.addAttribute("curvePoint", curvePointService.getCurvePointUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/update";
    }

    /**
     * Update curve point information
     * @param id curve point id
     * @param curvePoint curve point information
     * @param result result of validation
     * @param model if result is false, it will have curvePointUpdateDto and error message of validation
     * @return Update curve point page or curve point list page
     */
    @PostMapping("/curvePoint/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCurve(@PathVariable("id") Integer id, @Validated @ModelAttribute("curvePoint") CurvePointUpdateDto curvePoint,
                            BindingResult result, Model model) {
        if(result.hasErrors()) return "curvePoint/update";
        try {
            curvePointService.update(id, curvePoint);
        } catch (NoSuchElementException e) {
            return "redirect:/curvePoint/list";
        }
        return "redirect:/curvePoint/list";
    }

    /**
     * Delete a curve point
     * @param id curve point id
     * @return curve point list page
     */
    @GetMapping("/curvePoint/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCurve(@PathVariable("id") Integer id) {
        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}