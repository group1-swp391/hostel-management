package com.example.hostelmanagement.controllers.Hostel;

import com.example.hostelmanagement.models.Hostel;
import com.example.hostelmanagement.repositories.HostelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/Hostel")
public class HostelController {
    @Autowired
    private HostelRepository hostelRepository;

    @RequestMapping(value = "/getAllHostel1")
    public String getAllHostel1(ModelMap mm) {

        List<Hostel> listHostels = hostelRepository.getAllByHostelStatusTrue();
        mm.put("listHostels", listHostels);

        return "test";
    }

    @RequestMapping(value = "/getAllHostel")
    public String getAllHostel(ModelMap mm) {

        List<Hostel> listHostels = hostelRepository.getAllByHostelStatusTrue();
        mm.put("listHostels", listHostels);

        return "test/listhostel";
    }

}
