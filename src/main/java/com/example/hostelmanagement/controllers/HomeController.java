package com.example.hostelmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @RequestMapping(value = "home")
    public String getHome() {
        return "index";
    }

    @RequestMapping(value = "admin")
    public String getAdmin() {
        return "redirect:/api/v1/Admin/";
    }

    @RequestMapping(value = "to-rentroom")
    public String rentRoomSite() {
        return "redirect:/api/v1/rentroom/";
    }

    @RequestMapping(value = "test")
    public String test() {
        return "admin";
    }

}
