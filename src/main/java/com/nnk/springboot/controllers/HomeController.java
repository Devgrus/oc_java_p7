package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
    @RequestMapping("/")
    public String home(Model model)
    {
        return "home";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}