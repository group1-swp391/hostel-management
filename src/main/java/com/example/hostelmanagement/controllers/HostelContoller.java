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
    public String insertHostel(ModelMap mm, HttpSession session, @RequestParam("newAddress") String newAddress, @RequestParam("newHostelName") String newHostelName){
        try {
            User loginUser = (User) session.getAttribute("LOGIN_USER");
            hostelRepository.save(new Hostel(loginUser.getUserId(), newAddress, newHostelName, true));
            mm.put("message", "Insert new hostel successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        } finally {
            return getAllHostels(newHostelName, session, mm);
        }
    }

    @PostMapping(value = "delete")
    public String deleteHostel(HttpSession session,ModelMap mm, @RequestParam(value = "hostelId") int hostelId) {
        Hostel hostel = null;
        try {
            hostel = hostelRepository.findById(hostelId).get();
            hostel.setHostelStatus(false);
            hostelRepository.save(hostel);
            mm.put("message","Delete hostel successfully");
        } catch (Exception e) {
            mm.put("message", "Delete hostel failed");
        } finally {
            return getAllHostels(hostel.getHostelName(), session, mm);
        }
    }
    @PostMapping(value = "update")
    public String updateHostel(HttpSession session, ModelMap mm, @RequestParam(value = "hostelId") int hostelId, @RequestParam("hostelName") String hostelName, @RequestParam("address") String address) {
        try {
            Hostel hostel = hostelRepository.findById(hostelId).get();
            hostel.setHostelName(hostelName);
            hostel.setAddress(address);
            hostelRepository.save(hostel);
            mm.put("message","Update hostel successfully");
        } catch (Exception e) {
            mm.put("message", "Update hostel failed");
        } finally {
            return getAllHostels(hostelName, session, mm);
        }
    }

    @GetMapping(value = "search")
    public String getAllHostels(@RequestParam(value = "hostelName", required = false) String hostelName, HttpSession session, ModelMap mm) {

        mm.put("hostelName", hostelName);
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        mm.put("hostels", hostelRepository.findAllByOwnerHostelIdAndHostelNameContainsAndHostelStatus(loginUser.getUserId(),hostelName,true));
        return "host_hostelMngt";
    }
}