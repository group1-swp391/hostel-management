package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.models.Hostel;
import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "api/v1/hostel/")
public class HostelContoller {
    @Autowired
    private HostelRepository hostelRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_hostelMngt";
    }

    @PostMapping(value = "insertHostel")
    public String insertHostel(ModelMap mm, HttpSession session,@RequestParam("newAddress") String newAddress,@RequestParam("newHostelName") String newHostelName, @RequestParam("newQuantity") int newQuantity){
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        hostelRepository.save(new Hostel(loginUser.getUserId(), newAddress, newHostelName, newQuantity, true));
        mm.put("message", "Insert new hostel successfully!");
        return "host_hostelMngt";
    }

    @GetMapping(value = "hostel")
    public String getAllHostels(@RequestParam("hostelName") String hostelName, HttpSession session, ModelMap mm) {
        mm.put("hostelName", hostelName);
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        mm.put("hostels", hostelRepository.findAllByOwnerHostelID(hostelName, loginUser.getUserId()));
        return "host_hostelMngt";
    }
}
