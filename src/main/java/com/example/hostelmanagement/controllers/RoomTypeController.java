package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "api/v1/room-type")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private HostelRepository hostelRepository;

    private List<Hostel> getHostels(int userId) {
        List<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(userId);
        return hostels;
    }

    @PostMapping(value = "insert")
    public String insertRoomType(ModelMap mm, RoomType roomTypeObj) {
        roomTypeObj.setRoomTypeStatus(true);
        roomTypeRepository.save(roomTypeObj);

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
    public String updateRoomType(ModelMap mm, @RequestParam int typeId, @RequestParam String roomName,
                                 @RequestParam String description, @RequestParam double price,
                                 @RequestParam double depositPrice) throws IOException {
        RoomType roomType = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + typeId));

        roomType.setRoomName(roomName);
        roomType.setDescription(description);
        roomType.setPrice(price);
        roomType.setDepositPrice(depositPrice);
        //if (roomTImg.getSize()>0) roomType.setRoomTImg(Utils.getByteImage(roomTImg));

        roomTypeRepository.save(roomType);
        mm.put("message","Cập nhật loại phòng thành công");
        return "redirect:";
    }

    @RequestMapping(value = "")
    public String getAllRoomType(@RequestParam(required = false) Optional<Boolean> updateRoomType,
                                 @RequestParam(required = false) Optional<Integer> updateRoomTypeId,
                                 ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "redirect:/api/v1/user/login";
        }
        int owner = accSession.getUserId();
        List<RoomType> roomTypes = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(owner)
                .forEach(hostel -> roomTypes.addAll(hostel.getRoomTypesByHostelId()));

        if (updateRoomType.isPresent() && updateRoomTypeId.isPresent()) {
            if (updateRoomType.get()) {
                RoomType roomtype = roomTypeRepository.findById(updateRoomTypeId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy loại phòng"));
                mm.put("roomtype", roomtype);
            }
        }

        mm.put("roomTypes", roomTypes);
        mm.put("hostels", getHostels(owner));
        mm.addAttribute("roomTypeObj", new RoomType());
        return "roomtype";
    }

    @RequestMapping(value = "hostel/{id}")
    public String getAllRoomTypeByHostel(@PathVariable("id") int id, Model model, ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            mm.put("message", "Need login first");
            return "redirect:/api/v1/user/login";
        }

        int owner = accSession.getUserId();
        List<RoomType> roomTypes = new ArrayList<>();

        Hostel hostel = hostelRepository.findById(id).get();
        roomTypes.addAll(hostel.getRoomTypesByHostelId());
        mm.put("roomTypes", roomTypes);
        mm.put("hostels", getHostels(owner));
        model.addAttribute("roomTypeObj", new RoomType());
        return "roomtype";
    }
}