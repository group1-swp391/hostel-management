package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;


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
    private RoomChargeRepository roomChargeRepository;
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

    @RequestMapping(value = "delete")
    public String deleteRoom(RedirectAttributes redirectAttributes, @RequestParam int roomId) {
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
                             @RequestParam Part image, @RequestParam String description) throws IOException {
        //Check authorization here
        //...
        try{
            Room room = roomRepository.findById(roomId)
                                      .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng id: " + roomId));
            //Validate data here
            ///..
            room.setRoomNumber(roomNumber);
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

    @GetMapping(value = "")
    public String getAllRooms(ModelMap mm, HttpSession session,
                              @RequestParam(required = false) Optional<Boolean> updateRoom,
                              @RequestParam(required = false) Optional<Integer> updateRoomId) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Đăng nhập để tiếp tục");
            return "login";
        }
        int owner = accSession.getUserId();
        List<Room> rooms = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(owner)
                .forEach(hostel -> hostel.getRoomTypesByHostelId().forEach(roomType -> rooms.addAll(roomType.getRoomsByTypeId())));

        if (updateRoom.isPresent() && updateRoomId.isPresent()) {
            if (updateRoom.get()) {
                Room room = roomRepository.findById(updateRoomId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng!"));
                mm.put("room", room);
            }
        }



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

    @RequestMapping(value = "/{roomId}")
    public String getRoomDetails(@PathVariable("roomId") int roomId,
                                 @RequestParam(required = false) Optional<Integer> filterMonth,
                                 @RequestParam(required = false) Optional<Integer> filterYear,
                                 @RequestParam(required = false) Optional<Boolean> updateUsedUtility,
                                 @RequestParam(required = false) Optional<Integer> updateUsedUtilityId,
                                 @RequestParam(required = false) Optional<Boolean> updateUsedService,
                                 @RequestParam(required = false) Optional<Integer> updateUsedServiceId,
                                 @RequestParam(required = false) Optional<Boolean> updateRoomCharge,
                                 @RequestParam(required = false) Optional<Integer> updateRoomChargeId,
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
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiền phòng sử dụng"));
                mm.put("usedUtility", usedUtility);
            }
        }
        if (updateUsedService.isPresent() && updateUsedServiceId.isPresent()) {
            if (updateUsedService.get()) {
                UsedService usedService = usedServiceRepository.findById(updateUsedServiceId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiền phòng sử dụng"));
                mm.put("usedService", usedService);
            }
        }
        if (updateRoomCharge.isPresent() && updateRoomChargeId.isPresent()) {
            if (updateRoomCharge.get()) {
                RoomCharge roomCharge = roomChargeRepository.findById(updateRoomChargeId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiền phòng sử dụng"));
                mm.put("roomCharge", roomCharge);
            }
        }

        Collection<UsedUtility> usedUtilities = room.getTblUsedUtilitiesByRoomId();
        Collection<UsedUtility> usedUtilities1 = new ArrayList<>();

        Collection<UsedService> usedServices = room.getUsedServicesByRoomId();
        Collection<UsedService> usedServices1 = new ArrayList<>();

        Collection<Contracts> contracts = room.getContractsByRoomId();
        Collection<Contracts> contracts1 = new ArrayList<>();

        Collection<RoomCharge> roomCharges = room.getRoomChargesByRoomId();
        Collection<RoomCharge> roomCharges1 = new ArrayList<>();

        Collection<Invoice> invoices = room.getInvoicesByRoomId();
        Collection<Invoice> invoices1 = new ArrayList<>();

        if (filterYear.isPresent() && filterMonth.isPresent()) {
            for (UsedUtility item: usedUtilities) {
                Date dat = item.getStartDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == filterMonth.get() && year == filterYear.get()) {
                    usedUtilities1.add(item);
                }
            }
            mm.put("usedUtilities", usedUtilities1);

            for (UsedService item: usedServices) {
                Date dat = item.getStartDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == filterMonth.get() && year == filterYear.get()) {
                    usedServices1.add(item);
                }
            }
            mm.put("usedServices", usedServices1);

            for (Contracts item: contracts) {
                Date dat = item.getStartDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == filterMonth.get() && year == filterYear.get()) {
                    contracts1.add(item);
                }
            }
            mm.put("contracts", contracts1);

            for (RoomCharge item: roomCharges) {
                Date dat = item.getStartDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == filterMonth.get() && year == filterYear.get()) {
                    roomCharges1.add(item);
                }
            }
            mm.put("roomCharges", roomCharges1);

            for (Invoice item: invoices) {
                Date dat = item.getInvoiceCreateDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dat);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == filterMonth.get() && year == filterYear.get()) {
                    invoices1.add(item);
                }
            }
            mm.put("invoices", invoices1);

        } else {
            mm.put("invoices", invoices);
            mm.put("usedUtilities", usedUtilities);
            mm.put("usedServices", usedServices);
            mm.put("contracts", contracts);
            mm.put("roomCharges", roomCharges);
        }





        mm.put("room", room);

        mm.put("flashAttr", flashAttr+"");
        return "test3";
    }

}