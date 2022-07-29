package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "api/v1/roomcharge/")
public class RoomChargeController {
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsedServiceRepository usedServiceRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomChargeRepository roomChargeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @RequestMapping(value = "")
    public String getRoomChargeSite(ModelMap mm, HttpSession session) {
        return getAllRoomCharge(mm, session);
    }

    @RequestMapping(value = "getAllRoomCharge")
    public String getAllRoomCharge(ModelMap mm, HttpSession session) {
        //List<Invoice> listinvoices = invoiceRepository.findAllByInvoiceStatusIsTrue();


        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "login";
        }
        List<RoomCharge> roomcharges = roomChargeRepository.findAll();
        List<RoomCharge> roomcharges1 = new ArrayList<>();
        int owner = accSession.getUserId();
        for (RoomCharge roomcharge : roomcharges ) {
            int _owner = roomcharge.getRoomByRoomId().getRoomTypeByTypeId().getHostelByHostelId().getOwnerHostelId();
            if (owner == _owner) {
                roomcharges1.add(roomcharge);
            }
        }
        mm.put("roomcharges", roomcharges);
        return "roomcharge";
    }

    @PostMapping(value = "delete")
    public String deleteRoomCharge(
                                 @RequestParam int roomChargeId,
                                 ModelMap mm
    ) {
        Optional<RoomCharge> roomChargeOptional = roomChargeRepository.findById(roomChargeId);

        if (roomChargeOptional.isPresent()) {
            int roomId = roomChargeOptional.get().getRoomId();
            roomChargeRepository.delete(roomChargeOptional.get());
            mm.put("message","Xoá thành công tiền phòng");
            return "redirect:/api/v1/room/"+roomId;
        }
        return "error";
    }


    @PostMapping(value = "insert")
    public String insertRoomCharge(
            @RequestParam int roomId ,
            @RequestParam java.sql.Date startDate,
            @RequestParam java.sql.Date endDate,
            ModelMap mm,
            HttpSession session
    ) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + roomId));
        double price = room.getRoomTypeByTypeId().getPrice();
        RoomCharge roomCharge = RoomCharge.builder()
                .roomId(roomId)
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .build();
        roomChargeRepository.save(roomCharge);

        return "redirect:/api/v1/room/"+roomId;
    }
    @PostMapping(value ="update")
    public String updateUltility(@RequestParam int roomChargeId,
                                 @RequestParam(required = false) int roomId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam double price,
                                 ModelMap mm
    ) {
        Optional<RoomCharge> roomChargeOptional = roomChargeRepository.findById(roomChargeId);
        if (roomChargeOptional.isPresent()) {
            RoomCharge roomCharge = roomChargeOptional.get();
            roomCharge.setStartDate(startDate);
            roomCharge.setEndDate(endDate);
            roomCharge.setPrice(price);
            roomChargeRepository.save(roomCharge);
            mm.put("message","delete success");
        }
        return "redirect:/api/v1/room/"+roomId;
    }
}
