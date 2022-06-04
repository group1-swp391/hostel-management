package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "insert")
    public String insertHostel(ModelMap mm, HttpSession session,@RequestParam("newAddress") String newAddress,@RequestParam("newHostelName") String newHostelName, @RequestParam("newQuantity") int newQuantity){
        try {
            User loginUser = (User) session.getAttribute("LOGIN_USER");
            hostelRepository.save(new Hostel(loginUser.getUserId(), newAddress, newHostelName, newQuantity, true));
            mm.put("message", "Insert new hostel successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        }
        return "host_hostelMngt";
    }

    @GetMapping(value = "delete")
    public String deleteHostel(ModelMap mm, @RequestParam(value = "hostelID") int hostelID) {
        try {
            Hostel hostel = hostelRepository.findById(hostelID).get();
            hostel.setHostelStatus(false);
            hostelRepository.save(hostel);
            mm.put("message","Delete hostel successfully");
        } catch (Exception e) {
            mm.put("message", "Delete hostel failed");
        } finally {
            return "host_hostelMngt";
        }
    }
    @GetMapping(value = "update")
    public String updateHostel(ModelMap mm, @RequestParam(value = "hostelID") int hostelID, @RequestParam("hostelName") String hostelName, @RequestParam("address") String address, @RequestParam("room_quality") int room_quality) {
        try {
            Hostel hostel = hostelRepository.findById(hostelID).get();
            hostel.setHostelName(hostelName);
            hostel.setAddress(address);
            hostel.setRoom_quality(room_quality);
            hostelRepository.save(hostel);
            mm.put("message","Update hostel successfully");
        } catch (Exception e) {
            mm.put("message", "Update hostel failed");
        } finally {
            return "host_hostelMngt";
        }
    }

    @GetMapping(value = "search")
    public String getAllHostels(@RequestParam(value = "hostelName", required = false) String hostelName, HttpSession session, ModelMap mm) {

        mm.put("hostelName", hostelName);
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        mm.put("hostels", hostelRepository.findAllByOwnerHostelID(hostelName, loginUser.getUserId()));
        return "host_hostelMngt";
    }
}