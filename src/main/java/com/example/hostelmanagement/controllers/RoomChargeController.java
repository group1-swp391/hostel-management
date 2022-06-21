package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


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
        List<RoomCharge> roomcharges = roomChargeRepository.findAll();

//        try {
//            User accSession = (User) session.getAttribute("LOGIN_USER");
//            for (Invoice invoice: listinvoices) {
//                Room room = roomRepository.getRoomByRoomId(invoice.getRoomId());
//                if (room != null) {
//                    Hostel hostel = hostelRepository.getHostelByHostelId(room.getHostelId());
//                    if (accSession.getUserId() ==  hostel.getOwnerHostelId()) {
//                        invoices.add(invoice);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//
//        }

        mm.put("roomcharges", roomcharges);
        return "roomcharge";
    }


}
