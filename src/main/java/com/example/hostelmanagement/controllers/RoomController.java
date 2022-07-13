package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;


import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value ="api/v1/room")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostelRepository hostelRepository;

    private List<RoomType> getRoomTypes(int userId) {
        List<RoomType> roomTypes = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(userId)
                .forEach(hostel -> roomTypes.addAll(hostel.getRoomTypesByHostelId()));
        return roomTypes;
    }

    @PostMapping(value = "insert")
    public String insertRoom(RedirectAttributes redirectAttributes, @RequestParam int roomNumber, @RequestParam int typeId, @RequestParam Part image) throws IOException {
        //Check authorization
        Room room = Room.builder()
                .roomNumber(roomNumber)
                .typeId(typeId)
                .roomStatus(true)
                .build();
        if (image.getSize()>0) room.setImage(Utils.getByteImage(image));
        roomRepository.save(room);
        redirectAttributes.addAttribute("message", "Thêm phòng thành công");
        return "redirect:";
    }

    @PostMapping(value = "delete")
    public String deleteRoom(RedirectAttributes redirectAttributes, @RequestParam(value = "roomId") int roomId) {
        //Check authorization
        Room room = roomRepository.findById(roomId).get();
        room.setRoomStatus(false);
        roomRepository.save(room);
        redirectAttributes.addAttribute("message","Xóa phòng thành công");
        return "redirect:";
    }

    @PostMapping(value = "update")
    public String updateRoom(RedirectAttributes redirectAttributes, @RequestParam int roomId, @RequestParam int roomNumber,
                             @RequestParam int typeId , @RequestParam Part image) throws IOException {
       //Check authorization
        Room room = roomRepository.findById(roomId).get();
        room.setRoomNumber(roomNumber);
        room.setTypeId(typeId);
        if (image.getSize()>0) room.setImage(Utils.getByteImage(image));
        roomRepository.save(room);
        redirectAttributes.addFlashAttribute("message","Cập nhật phòng thành công");
        return "redirect:";
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
        mm.put("roomTypes", getRoomTypes(owner));
        return "adroom";
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