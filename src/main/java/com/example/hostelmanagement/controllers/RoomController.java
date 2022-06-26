package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
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
    private HostelRepository hostelRepository;
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
            mm.put("message","Xóa phòng thành công");
        } catch (Exception e) {
            mm.put("message", "Xóa phòng thất bại");
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
            return "/";
        }
    }


    @GetMapping(value = "/")
    public String getAllRooms(ModelMap mm, HttpSession session) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Đăng nhập để tiếp tục");
            return "login";
        }
        int owner = accSession.getUserId();
        List<Room> rooms = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(owner)
                .forEach(hostel -> hostel.getRoomTypesByHostelId().forEach(roomType -> rooms.addAll(roomType.getRoomsByTypeId())));
        mm.put("rooms", rooms);
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