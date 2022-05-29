package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "api/v1/User")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(value = "user") User user, ModelMap mm) {
        if (user.getUserName().equals("long")&&user.getPassword().equals("123")) {
            mm.put("message", user.getUserName());
            return "welcome";
        }else {
            mm.put("message", "Invalid account");
            return "login";
        }
    }
    @RequestMapping(value = "/register")
    public String register(@RequestParam("hostelId") String hostelId, ModelMap mm) {
        mm.put("hostelId", hostelId);
        return "register";
    }
    @PostMapping(value = "/register")
    public String register(@ModelAttribute(value = "user") User user, ModelMap mm) {
        if (!user.getUserName().equals("long")) {
            mm.put("error", "Register failed");
            return "register";
        }
        mm.put("message", user.getUserName());
        return "welcome";
    }
    @GetMapping(value = "/searchName/{userName}")
    public String searchName(@RequestParam("userName") String userName, ModelMap mm) {
        try {
            mm.put("userName", userName);
            List<User> users = userRepository.getAllByAName(userName);
        } catch (Exception e) {

        } finally {
            return "admin_userMngt";
        }
    }
}
