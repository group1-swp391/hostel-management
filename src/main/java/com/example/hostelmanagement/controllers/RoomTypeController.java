package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "api/v1/RoomType/")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_roomTypeMngt";
    }

    @PostMapping(value = "insert")
    public String insertRoomType(ModelMap mm, @RequestParam("newRoomName") String newRoomName, @RequestParam("newDescription") String newDescription, @RequestParam("newPrice") double newPrice, @RequestParam("newDepositPrice") double newDepositPrice) {
        try {
            roomTypeRepository.save(new RoomType(newDescription, newPrice, newDepositPrice, newRoomName, true));
            mm.put("message", "Insert new hostel successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        }
        return "host_roomTypeMngt";
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
            return "host_roomTypeMngt";
        }
    }
    @GetMapping(value = "update")
    public String updateRoomType(ModelMap mm, @RequestParam(value = "typeId") int typeId, @RequestParam("roomName") String roomName, @RequestParam("description") String description, @RequestParam("price") double price, @RequestParam("depositPrice") double depositPrice) {
        try {
            RoomType roomType = roomTypeRepository.findById(typeId).get();
            roomType.setRoomName(roomName);
            roomType.setDescription(description);
            roomType.setPrice(price);
            roomType.setDepositPrice(depositPrice);
            roomTypeRepository.save(roomType);
            mm.put("message","Update room type successfully");
        } catch (Exception e) {
            mm.put("message", "Update room type failed");
        } finally {
            return "host_roomTypeMngt";
        }
    }

    @GetMapping(value = "search")
    public String getAllRoomType(@RequestParam(value = "roomName", required = false) String roomName, ModelMap mm) {
        mm.put("roomName", roomName);
        mm.put("roomTypes", roomTypeRepository.findAllByRoomNameAndRoomTypeStatus(roomName, true));
        return "host_roomTypeMngt";
    }
}