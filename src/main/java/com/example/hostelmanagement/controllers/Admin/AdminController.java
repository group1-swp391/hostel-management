package com.example.hostelmanagement.controllers.Admin;

import com.example.hostelmanagement.models.Hostel;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/Admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/getAllUser")
    public String getAllHostel1(ModelMap mm) {

        List<Hostel> listHostels = hostelRepository.getAllByHostelStatusTrue();
        mm.put("listHostels", listHostels);

        return "test";
    }


}
