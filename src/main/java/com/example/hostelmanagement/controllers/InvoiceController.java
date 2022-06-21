package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Utils;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "api/v1/invoice/")
public class InvoiceController {
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

    @RequestMapping(value = "/callBillByRoomId")
    public String callBillByRoomId(ModelMap mm,
                                   @RequestParam int roomId,
                                   @RequestParam String invoiceName,
                                   @RequestParam(required = false, defaultValue = "") String note,
                                   HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            //CHECK Authorization owner room
            boolean ownerCheck = false;
            int hostelId = 0;
            try {
                Room room = roomRepository.getRoomByRoomId(roomId);
                RoomType roomType = roomTypeRepository.getRoomTypeByTypeId(room.getTypeId());
                hostelId = roomType.getHostelId();
                Hostel hostel = hostelRepository.getHostelByHostelId(hostelId);
                int ownerId = hostel.getOwnerHostelId();
                if (ownerId == accSession.getUserId()) {
                    ownerCheck = true;
                } else {
                    mm.put("message", "Not owner this hostel");
                }
            } catch (Exception ex) {
                mm.put("message", " Something error! " + ex.getMessage());
            }

            if (ownerCheck) {
                List<RoomCharge> roomChargeList = roomChargeRepository.getAllByRoomIdAndInvoiceIdNull(roomId);
                List<UsedService> usedServiceList = usedServiceRepository.findAllByInvoiceIdNullAndRoomId(roomId);
                List<UsedUtility> usedUtilityList = usedUtilityRepository.findAllByInvoiceIdNullAndRoomId(roomId);

                if (roomChargeList.isEmpty() && usedServiceList.isEmpty() && usedUtilityList.isEmpty()) {
                    mm.put("message", "Nothing to invoice");
                } else {
                    java.util.Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());

                    Invoice invoice = new Invoice(roomId, invoiceName, (double) 0, true, note, ts, null);
                    invoiceRepository.save(invoice);

                    double total = 0;

                    for (RoomCharge roomCharge : roomChargeList) {
                        total += roomCharge.getPrice();

                        roomCharge.setInvoiceId(invoice.getInvoiceId());
                        roomCharge = roomChargeRepository.save(roomCharge);
                    }

                    for (UsedService usedService : usedServiceList) {
                        total += usedService.getPrice() * (double) usedService.getUsedQuantity();

                        usedService.setInvoiceId(invoice.getInvoiceId());
                        usedService = usedServiceRepository.save(usedService);
                    }

                    for (UsedUtility usedUtility : usedUtilityList) {
                        total += usedUtility.getPricePerIndex() * (double) (usedUtility.getNewIndex() - usedUtility.getOldIndex());

                        usedUtility.setInvoiceId(invoice.getInvoiceId());
                        usedUtility = usedUtilityRepository.save(usedUtility);
                    }

                    invoice.setTotalAmount(total);
                    invoice = invoiceRepository.save(invoice);

                    mm.put("message", "Invoice id: " + invoice.getInvoiceId());
                }
                return "test2";
            } else {
                return "test2";
            }
        } else {
            mm.put("message", "Need login first");
            return "test2";
        }
    }

}
