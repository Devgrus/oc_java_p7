package com.nnk.springboot.controllers;

import com.nnk.springboot.config.auth.CustomUserDetails;
import com.nnk.springboot.dto.user.UserAddDto;
import com.nnk.springboot.dto.user.UserUpdateDto;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User list page
     * @param model user list
     * @return user list page
     */
    @RequestMapping("/user/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getAllUserList());
        return "user/list";
    }

    /**
     * Add user page
     * @param user new user information
     * @return add user page
     */
    @GetMapping("/user/add")
    @PreAuthorize("isAnonymous() or hasRole('ADMIN')")
    public String addUser(@ModelAttribute("user") UserAddDto user) {
        return "user/add";
    }

    /**
     * Validate a new user information and add user
     * @param user user information
     * @param result result of validation
     * @param model if result is false, it will have user and error message of validation
     * @return add user page or user list page
     */
    @PostMapping("/user/validate")
    @PreAuthorize("isAnonymous() or hasRole('ADMIN')")
    public String validate(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Validated @ModelAttribute("user") UserAddDto user, BindingResult result, Model model) {
        if (result.hasErrors()) return "user/add";
        try {
            userService.save(user.toEntity());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/add";
        }
        if(customUserDetails == null) return "redirect:/login";
        return "redirect:/user/list";
    }

    /**
     * Update user page
     * @param id user id
     * @param model user information
     * @return update user page or user list page
     */
    @GetMapping("/user/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getUserUpdateFormData(id));
        } catch (NoSuchElementException e) {
            return "redirect:/user/list";
        }
        return "user/update";
    }

    /**
     * Update user information
     * @param id user id
     * @param user user information
     * @param result result of validation
     * @param model if result is false, it will return user and error message of validation
     * @return update user page or user list page
     */
    @PostMapping("/user/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable("id") Integer id, @Validated @ModelAttribute("user") UserUpdateDto user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) return "user/update";
        try {
            userService.update(id, user);
        } catch (NoSuchElementException e) {
            return "redirect:/user/list";
        }

        return "redirect:/user/list";
    }

    /**
     * Delete a user
     * @param id user id
     * @return user list page
     */
    @GetMapping("/user/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}