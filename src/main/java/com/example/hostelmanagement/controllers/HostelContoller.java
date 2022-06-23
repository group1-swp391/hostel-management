package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/hostel/")
public class HostelContoller {
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private UserRepository userRepository;


//    @PostMapping(value = "insert")
//    public String insertHostel(ModelMap mm, HttpSession session, @RequestParam("newAddress") String newAddress, @RequestParam("newHostelName") String newHostelName){
//        try {
//            User loginUser = (User) session.getAttribute("LOGIN_USER");
//            //hostelRepository.save(new Hostel(loginUser.getUserId(), newAddress, newHostelName, true));
//
//            mm.put("message", "Insert new hostel successfully!");
//        }catch (Exception e) {
//            mm.put("message", "Insert new hostel failed!");
//        } finally {
//            return getAllHostels(newHostelName, session, mm);
//        }
//    }
//
//    @PostMapping(value = "delete")
//    public String deleteHostel(HttpSession session,ModelMap mm, @RequestParam(value = "hostelId") int hostelId) {
//        Hostel hostel = null;
//        try {
//            hostel = hostelRepository.findById(hostelId).get();
//            hostel.setHostelStatus(false);
//            hostelRepository.save(hostel);
//            mm.put("message","Delete hostel successfully");
//        } catch (Exception e) {
//            mm.put("message", "Delete hostel failed");
//        } finally {
//            return getAllHostels(hostel.getHostelName(), session, mm);
//        }
//    }
//    @PostMapping(value = "update")
//    public String updateHostel(HttpSession session, ModelMap mm, @RequestParam(value = "hostelId") int hostelId, @RequestParam("hostelName") String hostelName, @RequestParam("address") String address) {
//        try {
//            Hostel hostel = hostelRepository.findById(hostelId).get();
//            hostel.setHostelName(hostelName);
//            hostel.setAddress(address);
//            hostelRepository.save(hostel);
//            mm.put("message","Update hostel successfully");
//        } catch (Exception e) {
//            mm.put("message", "Update hostel failed");
//        } finally {
//            return getAllHostels(hostelName, session, mm);
//        }
//    }
//
//    @GetMapping(value = "search")
//    public String getAllHostels(@RequestParam(value = "hostelName", required = false) String hostelName, HttpSession session, ModelMap mm) {
//
//        mm.put("hostelName", hostelName);
//        User loginUser = (User) session.getAttribute("LOGIN_USER");
//        mm.put("hostels", hostelRepository.findAllByOwnerHostelIdAndHostelNameContainsAndHostelStatus(loginUser.getUserId(),hostelName,true));
//        return "host_hostelMngt";
//    }


    @RequestMapping(value = "getAllHostel")
    public String getAllHostel(ModelMap mm) {

        List<Hostel> hostels = hostelRepository.getAllByHostelStatusTrue();
        mm.put("listHostels", hostels);

        return "hostel";
    }

    @RequestMapping(value = "getHostel/{hostelid}")
    public String getHostel(@PathVariable("hostelid") int hostelid,
                            ModelMap mm) {

        if (hostelRepository.existsById(hostelid) && hostelRepository.findById(hostelid).get().isHostelStatus()) {
            Hostel hostel = hostelRepository.findById(hostelid).get();
            mm.put("hostel", hostel);
        } else {
            mm.put("message", "Not found hostel id " + hostelid);
        }

        return getAllHostel(mm);
    }

    @PostMapping(value = "createHostel")
    public String createHostel(@RequestParam String address,
                               @RequestParam String hostelName,
                               ModelMap mm, HttpSession session) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        int ownerHostelId = accSession.getUserId();

        Optional<User> userOptional = userRepository.findById(ownerHostelId);
        if (!userOptional.isPresent() || !userOptional.get().isUserStatus() || userOptional.get().getRoleId() == 3) {
            mm.put("message", "Not found userid or user role is customers!");
        } else {
            try {
                Hostel hostel = Hostel.builder()
                        .ownerHostelId(ownerHostelId)
                        .hostelName(hostelName)
                        .address(address)
                        .hostelStatus(true)
                        .build();

                hostel = hostelRepository.save(hostel);

                mm.put("message",  "Create success hostel id: " + hostel.getHostelId());
            } catch (Exception e) {
                mm.put("message", "Create hostel error!");
            }
        }

        return getAllHostel(mm);
    }

    @PostMapping(value = "updateHostel/{hostelid}")
    public String updateHostel(@PathVariable("hostelid") int hostelid,
                               @RequestParam int ownerHostelId,
                               @RequestParam String address,
                               @RequestParam String hostelName,
                               ModelMap mm) {


        Optional<User> userOptional = userRepository.findById(ownerHostelId);

        if (!userOptional.isPresent() || !userOptional.get().isUserStatus() || userOptional.get().getRoleId() != 2) {
            mm.put("message", "Not found userid or user role not hostel owner!");
        } else if (!hostelRepository.existsById(hostelid) || !hostelRepository.findById(hostelid).get().isHostelStatus()) {
            mm.put("message", "Not found hostel id");
        }
        else {
            try {

                Hostel hostel = hostelRepository.findById(hostelid).get();
                hostel.setOwnerHostelId(ownerHostelId);
                hostel.setHostelName(hostelName);
                hostel.setAddress(address);

                hostel = hostelRepository.save(hostel);

                mm.put("message",  "Update success hostel id: " + hostel.getHostelId());
            } catch (Exception e) {
                mm.put("message", "Update hostel error!");
            }
        }

        return getAllHostel(mm);
    }

    @RequestMapping(value = "deleteHostel")
    public String deleteHostel(@RequestParam("hostelid") int hostelid, ModelMap mm) {

        Hostel hostel = hostelRepository.findById(hostelid).get();
        if (hostel != null && hostel.isHostelStatus()) {
            hostel.setHostelStatus(false);
            hostel = hostelRepository.save(hostel);
            if(!hostel.isHostelStatus())
                mm.put("message", "Delete Success Hostel ID: " + hostel.getHostelId());
        } else {
            mm.put("message", "Not found hostel id");
        }

        return getAllHostel(mm);
    }

}