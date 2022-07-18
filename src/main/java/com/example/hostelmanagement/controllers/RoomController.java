package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;


import com.example.hostelmanagement.repositories.UsedServiceRepository;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
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
import java.util.*;

@Controller
@RequestMapping(value ="api/v1/room")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private UsedServiceRepository usedServiceRepository;

    private List<RoomType> getRoomTypes(int userId) {
        List<RoomType> roomTypes = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(userId)
                .forEach(hostel -> roomTypes.addAll(hostel.getRoomTypesByHostelId()));
        return roomTypes;
    }

    @PostMapping(value = "insert")
    public String insertRoom(RedirectAttributes redirectAttributes, @RequestParam int roomNumber, @RequestParam int typeId, @RequestParam Part image, @RequestParam String description) throws IOException {
        //Check authorization
        //...

        //Validate data here
        ///..
        Room room = Room.builder()
                        .roomNumber(roomNumber)
                        .typeId(typeId)
                        .description(description)
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
        //...
        Room room = roomRepository.findById(roomId)
                                  .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng id: " + roomId));

        room.setRoomStatus(false);
        roomRepository.save(room);

        redirectAttributes.addAttribute("message","Xóa phòng thành công");
        return "redirect:";
    }

    @PostMapping(value = "update")
    public String updateRoom(RedirectAttributes redirectAttributes, @RequestParam int roomId, @RequestParam int roomNumber,
                             @RequestParam int typeId , @RequestParam Part image, @RequestParam String description) throws IOException {
       //Check authorization here
        //...
        try{
            Room room = roomRepository.findById(roomId)
                                      .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng id: " + roomId));
            //Validate data here
            ///..
            room.setRoomNumber(roomNumber);
            room.setTypeId(typeId);
            room.setDescription(description);

            if (image.getSize()>0) room.setImage(Utils.getByteImage(image));
            roomRepository.save(room);
            redirectAttributes.addFlashAttribute("message","Cập nhật phòng thành công");
            return "redirect:";
        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message","Lỗi cập nhật phòng");
            return "redirect:";
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

    //DETAILS  ROOM
    @RequestMapping(value = "/{roomId}")
    public String getRoomDetails(@PathVariable("roomId") int roomId,
                                 @RequestParam(required = false) Optional<Boolean> updateUsedUtility,
                                 @RequestParam(required = false) Optional<Integer> updateUsedUtilityId,
                                 @RequestParam(required = false) Optional<Boolean> updateUsedService,
                                 @RequestParam(required = false) Optional<Integer> updateUsedServiceId,
                                 @ModelAttribute("flashAttr") String flashAttr,
                                 ModelMap mm,
                                 HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            mm.put("message", "Đăng nhập để tiếp tục");
            return "login";
        }
        int owner = accSession.getUserId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng id: " + roomId));

        int _owner = room.getRoomTypeByTypeId().getHostelByHostelId().getOwnerHostelId();
        if (owner != _owner) {
            mm.put("message", "Không có quyền truy cập!");
            return "error";
        }

        if (updateUsedUtility.isPresent() && updateUsedUtilityId.isPresent()) {
            if (updateUsedUtility.get()) {
                UsedUtility usedUtility = usedUtilityRepository.findById(updateUsedUtilityId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiện ích sử dụng"));
                mm.put("usedUtility", usedUtility);
            }
        }
        if (updateUsedService.isPresent() && updateUsedServiceId.isPresent()) {
            if (updateUsedService.get()) {
                UsedService usedService = usedServiceRepository.findById(updateUsedServiceId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ sử dụng"));
                mm.put("usedService", usedService);
            }
        }

        Collection<UsedUtility> usedUtilities = room.getTblUsedUtilitiesByRoomId();
        Collection<UsedService> usedServices = room.getUsedServicesByRoomId();

        mm.put("usedUtilities", usedUtilities);
        mm.put("usedServices", usedServices);
        mm.put("room", room);
        mm.put("flashAttr", flashAttr+"");
        return "test3";
    }


}