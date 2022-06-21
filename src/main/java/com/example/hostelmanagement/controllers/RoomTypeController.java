package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Part;


@Controller
@RequestMapping(value = "api/v1/RoomType/")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "redirect:search?roomName=";
    }

    @PostMapping(value = "insert")
    public String insertRoomType(ModelMap mm, @RequestParam("hostelId") int hostelId, @RequestParam("newRoomName") String newRoomName, @RequestParam("newDescription") String newDescription, @RequestParam("newPrice") double newPrice, @RequestParam("newDepositPrice") double newDepositPrice, @RequestParam("newRoomTImg") Part newRoomTImg) {
        try {
            roomTypeRepository.save(new RoomType(hostelId, newDescription, newPrice, newDepositPrice, Utils.getByteImage(newRoomTImg), newRoomName, true));
            mm.put("message", "Insert new hostel successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        }
        return "roomtype";
    }

    @GetMapping(value = "delete")
    public String deleteRoomType(ModelMap mm, @RequestParam(value = "typeId") int typeId) {
        try {
            RoomType roomType = roomTypeRepository.findById(typeId).get();
            roomType.setRoomTypeStatus(false);
            roomTypeRepository.save(roomType);
            mm.put("message","Delete room type successfully");
        } catch (Exception e) {
            mm.put("message", "Delete room type failed");
        } finally {
            return "roomtype";
        }
    }
    @GetMapping(value = "update")
    public String updateRoomType(ModelMap mm, @RequestParam(value = "typeId") int typeId, @RequestParam("roomName") String roomName, @RequestParam("description") String description, @RequestParam("price") double price, @RequestParam("depositPrice") double depositPrice, @RequestParam("roomTImg") Part roomTImg) {
        try {
            RoomType roomType = roomTypeRepository.findById(typeId).get();
            roomType.setRoomName(roomName);
            roomType.setDescription(description);
            roomType.setPrice(price);
            roomType.setDepositPrice(depositPrice);
            roomType.setRoomTImg(Utils.getByteImage(roomTImg));
            roomTypeRepository.save(roomType);
            mm.put("message","Update room type successfully");
        } catch (Exception e) {
            mm.put("message", "Update room type failed");
        } finally {
            return "roomtype";
        }
    }

    @GetMapping(value = "search")
    public String getAllRoomType(@RequestParam(value = "roomName", required = false) String roomName, ModelMap mm) {
        mm.put("roomName", roomName);
        mm.put("roomTypes", roomTypeRepository.findAllByRoomNameContainsAndRoomTypeStatus(roomName, true));
        return "roomtype";
    }
}