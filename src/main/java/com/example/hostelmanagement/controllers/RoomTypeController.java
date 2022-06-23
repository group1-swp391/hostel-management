package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "api/v1/RoomType/")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @GetMapping(value = "/")
    public String roomTypeIndex() {

        return "redirect:search?roomName=";
    }

    @PostMapping(value = "insert")
    public String insertRoomType(ModelMap mm, @RequestParam("hostelId") int hostelId, @RequestParam("roomName") String roomName, @RequestParam("description") String description, @RequestParam("price") double price, @RequestParam("depositPrice") double depositPrice) {
        try {
            RoomType roomType = RoomType.builder()
                    .hostelId(hostelId)
                    .roomName(roomName)
                    .description(description)
                    .roomTypeStatus(true)
                    .price(price)
                    .depositPrice(depositPrice)

                    .build();
            roomTypeRepository.save(roomType);

            mm.put("message", "Insert new room type successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        }
        return "roomtype";
    }

    @PostMapping (value = "delete")
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
    @PostMapping(value = "update")
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
            return roomTypeIndex();
        }
    }

    @GetMapping(value = "search")
    public String getAllRoomType(@RequestParam(value = "roomName", required = false) String roomName, ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "error";
        }
        int owner = accSession.getUserId();
        List<RoomType> listRoomtype1 = roomTypeRepository.findAllByRoomNameContainsAndRoomTypeStatus(roomName, true);
        List<RoomType> listRoomtype2 = new ArrayList<>();
        for (RoomType roomType: listRoomtype1) {
            if (owner == roomType.getHostelByHostelId().getOwnerHostelId()) {
                listRoomtype2.add(roomType);
            }
        }

        mm.put("roomName", roomName);
        mm.put("roomTypes", listRoomtype2);

        return "roomtype";
    }
}