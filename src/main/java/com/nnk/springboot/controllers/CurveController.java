
package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointAddDto;
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
    public String addBidForm(CurvePointAddDto curvePointAddDto) {
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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Validated CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}