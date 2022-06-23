package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.RoomRepository;

import com.example.hostelmanagement.repositories.RoomTypeRepository;

import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"api/v1/host/","api/v1/host/room"})
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @RequestMapping(value = "test")


    @PostMapping(value = "insert")
    public String insertRoom(ModelMap mm, HttpSession session, @RequestParam("roomNumber") int roomNumber, @RequestParam("typeId") int typeId, @RequestParam("image") Part image) throws IOException {
        try {
            User loginUser = (User) session.getAttribute("LOGIN_USER");
            //Check authorization
            Room room = Room.builder()
                    .roomNumber(roomNumber)
                    .typeId(typeId)
                    .image(Utils.getByteImage(image))
                    .build();
            roomRepository.save(room);
            mm.put("message", "Insert new room successfully!");
        }catch (Exception e) {
            mm.put("message", "Insert new room failed!");
        }
        return "redirect:";
    }

    @PostMapping(value = "delete")
    public String deleteRoom(ModelMap mm, @RequestParam(value = "roomId") int roomId) {
        //Check authorization...
        try {
            Room room = roomRepository.findById(roomId).get();
            room.setRoomStatus(false);
            roomRepository.save(room);
            mm.put("message","Delete room successfully");
        } catch (Exception e) {
            mm.put("message", "Delete room failed");
        } finally {
            return "redirect:";
        }
    }

    @PostMapping(value = "update")
    public String updateRoom(ModelMap mm, @RequestParam(value = "roomId") int roomId, @RequestParam("roomNumber") int roomNumber, @RequestParam("image") Part image) {
       //Check authorization
        try {
            Room room = roomRepository.findById(roomId).get();
            room.setRoomNumber(roomNumber);
            if (image.getSize()>0) room.setImage(Utils.getByteImage(image));
            roomRepository.save(room);
            mm.put("message","Update room successfully");
        } catch (Exception e) {
            mm.put("message", "Update room failed");
        } finally {
            return "redirect:";
        }
    }


    @GetMapping(value = {"/","search"})
    public String getAllRooms(@RequestParam(value = "roomNumber", required = false) String roomNumber, ModelMap mm, HttpSession session) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "error";
        }
        int owner = accSession.getUserId();

        List<Room> rooms = roomRepository.findAllByRoomStatus(true);
        List<Room> rooms2 = new ArrayList<>();
        if (roomNumber!=null && !"".equals(roomNumber.trim())) {
            rooms = roomRepository.findAllByRoomNumberAndRoomStatus(Integer.parseInt(roomNumber), true);
        }

        for (Room room :rooms) {
             if (owner == room.getRoomTypeByTypeId().getHostelByHostelId().getOwnerHostelId()) {
                rooms2.add(room);
            }
        }
        mm.put("rooms", rooms2);
        return "hostpage";
    }
    @ResponseBody
    @GetMapping("image/{id}")
    public ResponseEntity<byte[]> fromDatabaseAsResEntity(@PathVariable("id") int id) {
            Optional<Room> room = roomRepository.findById(id);
            byte[] imageBytes = null;
            if (room.isPresent()) {
                imageBytes = room.get().getImage();
            }
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}