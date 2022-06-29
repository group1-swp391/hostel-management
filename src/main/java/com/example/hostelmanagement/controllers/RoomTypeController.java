package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "api/v1/RoomType")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private HostelRepository hostelRepository;

    private List<Hostel> getHostels(int userId) {
        List<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(userId);
        return hostels;
    }
    @RequestMapping(value = "add-room-type")
    public String addroomTypeSite(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }
        int owner = accSession.getUserId();
        mm.put("hostels", getHostels(owner));
        return "addroomtype";
    }

    @PostMapping(value = "insert")
    public String insertRoomType(ModelMap mm, @RequestParam int hostelId, @RequestParam String roomName,
                                 @RequestParam double price, @RequestParam double depositPrice) {
        RoomType roomType = RoomType.builder()
                .hostelId(hostelId)
                .roomName(roomName)
                .roomTypeStatus(true)
                .price(price)
                .depositPrice(depositPrice)
                .build();
        roomTypeRepository.save(roomType);

        mm.put("message", "Thêm loại phòng thành công");
        return "redirect:";
    }

    @PostMapping (value = "delete")
    public String deleteRoomType(ModelMap mm, @RequestParam int typeId) {
        RoomType roomType = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + typeId));

        roomType.setRoomTypeStatus(false);
        roomTypeRepository.save(roomType);
        mm.put("message","Xóa loại phòng thành công");
        return "redirect:";
    }
    @PostMapping(value = "update")
    public String updateRoomType(ModelMap mm, @RequestParam int typeId, @RequestParam String roomName, @RequestParam int hostelId,
                                 @RequestParam String description, @RequestParam double price,
                                 @RequestParam double depositPrice, @RequestParam Part roomTImg) throws IOException {
        RoomType roomType = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + typeId));

        roomType.setRoomName(roomName);
        roomType.setDescription(description);
        roomType.setPrice(price);
        roomType.setDepositPrice(depositPrice);
        if (roomTImg.getSize()>0) roomType.setRoomTImg(Utils.getByteImage(roomTImg));
        if (roomType.getRoomsByTypeId().isEmpty()) {
            roomType.setHostelId(hostelId);
        }
        roomTypeRepository.save(roomType);
        mm.put("message","Cập nhật loại phòng thành công");
        return "redirect:";
    }

    @RequestMapping(value = "/")
    public String getAllRoomType(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "/api/v1/user/login";
        }
        int owner = accSession.getUserId();
        List<RoomType> roomTypes = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(owner)
                .forEach(hostel -> roomTypes.addAll(hostel.getRoomTypesByHostelId()));
        mm.put("roomTypes", roomTypes);
        mm.put("hostels", getHostels(owner));
        return "roomtype";
    }
}