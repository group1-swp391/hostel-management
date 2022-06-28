package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.ServiceType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.ServiceTypeRepository;
import com.example.hostelmanagement.repositories.UsedServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "api/v1/service/")
public class ServiceController {
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private UsedServiceRepository usedServiceRepository;

    @RequestMapping (value = "add-service")
    public String addServiceTypeSite(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }
        int owner = accSession.getUserId();
        return "addservices";
    }
    @RequestMapping(value = "update-service")
    public String updateServiceType(HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }
        return "updateservices";
    }
    @PostMapping(value = "insert-type")
    public String insertType(@RequestParam String serviceName, @RequestParam double price) {

        return "redirect:";
    }

    @GetMapping(value = "/")
    public String getAllServices() {

        return "services";
    }


}