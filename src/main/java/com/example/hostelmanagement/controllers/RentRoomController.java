package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/rentroom")
public class RentRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @GetMapping(value = {"/","search"})

    public String getAllRooms(@RequestParam(value = "roomNumber", required = false) String roomNumber, ModelMap mm) {
        if (roomNumber==null || "".equals(roomNumber.trim())) {
            mm.put("roomNumber", roomNumber);
            List<Room> rooms = roomRepository.findAllByRoomStatus(true);
            rooms.forEach(room -> Utils.putPriceAndTypeNameToRoom(roomTypeRepository,room));
            mm.put("rooms", rooms);
        }
        else {
            mm.put("roomNumber", roomNumber);
            List<Room> rooms = roomRepository.findAllByRoomNumberAndRoomStatus(Integer.parseInt(roomNumber), true);
            rooms.forEach(room -> Utils.putPriceAndTypeNameToRoom(roomTypeRepository, room));
            mm.put("rooms", rooms);
        }
        return "rentroom";
    }
    @GetMapping(value = "/{id}/info-room")
    public String infoRoom(@PathVariable("id") int id, ModelMap mm) {
        Room room = roomRepository.findById(id).get();
        if (room!=null) {
            mm.put("room", room);
        }
        return "inforoom";
    }
    @RequestMapping(value = "booking")
    public String booking() {
        return "booking";
    }
}
