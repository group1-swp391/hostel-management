package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/Room/")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_roomMngt";
    }

    private byte[] getByteImage(Part image) throws IOException {
        return image.getInputStream().readAllBytes();
    }

    @PostMapping(value = "insert")
    public String insertRoom(ModelMap mm, HttpSession session, @RequestParam("newRoomNumber") int newRoomNumber, @RequestParam("newTypeId") int newTypeId, @RequestParam("hostelId") int hostelId, @RequestParam("newImage") Part newImage) throws IOException {
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        roomRepository.save(new Room(newRoomNumber, loginUser.getUserId(), newTypeId, true, getByteImage(newImage), hostelId));
        mm.put("message", "Insert new hostel successfully!");
        return "host_roomMngt";
    }

    @PostMapping(value = "delete")
    public String deleteRoom(ModelMap mm, @RequestParam(value = "roomId") int roomId) {
        try {
            Room room = roomRepository.findById(roomId).get();
            room.setRoomStatus(false);
            roomRepository.save(room);
            mm.put("message","Delete room successfully");
        } catch (Exception e) {
            mm.put("message", "Delete room failed");
        } finally {
            return "host_roomMngt";
        }
    }

    @PostMapping(value = "update")
    public String updateRoom(ModelMap mm, @RequestParam(value = "roomId") int roomId, @RequestParam("roomNumber") int roomNumber, @RequestParam("typeId") int typeId, @RequestParam("image") Part image) {
        try {
            Room room = roomRepository.findById(roomId).get();
            room.setRoomNumber(roomNumber);
            room.setTypeId(typeId);
            room.setImage(getByteImage(image));
            roomRepository.save(room);
            mm.put("message","Update room successfully");
        } catch (Exception e) {
            mm.put("message", "Update room failed");
        } finally {
            return "host_roomMngt";
        }
    }

    @GetMapping(value = "search")
    public String getAllRooms(@RequestParam("roomNumber") int roomNumber, ModelMap mm) {
        mm.put("roomNumber", roomNumber);
        mm.put("rooms", roomRepository.findAllByRoomNumber(roomNumber));
        return "host_roomMngt";
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