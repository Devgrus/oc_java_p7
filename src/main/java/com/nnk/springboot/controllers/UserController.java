package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserAddDto;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String home(Model model)
    {
//        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Add user page
     * @param user new user information
     * @return add user page
     */
    @GetMapping("/user/add")
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
    public String validate(@Validated @ModelAttribute("user") UserAddDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            user.setPassword(encoder.encode(user.getPassword()));
            return "user/add";
        }
        userService.save(user.toEntity());
        return "redirect:/user/list";
    }

    /**
     * Update user page
     * @param id user id
     * @param model user information
     * @return update user page or user list page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        user.setPassword("");
//        model.addAttribute("user", user);
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
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setId(id);
//        userRepository.save(user);
//        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * Delete a user
     * @param id user id
     * @return user list page
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        userRepository.delete(user);
//        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}