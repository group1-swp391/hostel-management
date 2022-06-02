package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "api/v1/Room/")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_roomMngt";
    }

//    @PostMapping(value = "insert")
//    public String insertRoom(ModelMap mm, HttpSession session, @RequestParam("newRoomNumber") int newRoomNumber, @RequestParam("newUserID") int newUserID, @RequestParam("newTypeID") int newTypeID){
//        User loginUser = (User) session.getAttribute("LOGIN_USER");
//        roomRepository.save(new Room(newRoomNumber, loginUser.getUserId(), newTypeID, true));
//        mm.put("message", "Insert new hostel successfully!");
//        return "host_roomMngt";
//    }
//
//    @GetMapping(value = "delete")
//    public String deleteRoom(ModelMap mm, @RequestParam(value = "RoomId") int roomId) {
//        try {
//            Room room = roomRepository.findById(roomId).get();
//            room.setRoomStatus(false);
//            roomRepository.save(room);
//            mm.put("message","Delete room successfully");
//        } catch (Exception e) {
//            mm.put("message", "Delete room failed");
//        } finally {
//            return "host_roomMngt";
//        }
//    }
//    @GetMapping(value = "update")
//    public String updateRoom(ModelMap mm, @RequestParam(value = "hostelID") int hostelID, @RequestParam("hostelName") String hostelName, @RequestParam("address") String address, @RequestParam("room_quality") int room_quality) {
//        try {
//            Hostel hostel = roomRepository.findById(hostelID).get();
//            hostel.setHostelName(hostelName);
//            hostel.setAddress(address);
//            hostel.setRoom_quality(room_quality);
//            roomRepository.save(hostel);
//            mm.put("message","Update hostel successfully");
//        } catch (Exception e) {
//            mm.put("message", "Update hostel failed");
//        } finally {
//            return "host_roomMngt";
//        }
//    }

    @GetMapping(value = "search")
    public String getAllRooms(@RequestParam("roomNumber") int roomNumber, HttpSession session, ModelMap mm) {
        mm.put("roomNumber", roomNumber);
        mm.put("hostels", roomRepository.findAllByRoomNumber(roomNumber));
        return "host_roomMngt";
    }
}