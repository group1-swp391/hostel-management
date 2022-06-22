package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;


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

    @RequestMapping(value = "")
    public String getInvoiceSite(ModelMap mm, HttpSession session) {
        return getAllInvoices(mm, session);
    }

    @RequestMapping(value = "getAllInvoice")
    public String getAllInvoices(ModelMap mm, HttpSession session) {
        List<Invoice> listinvoices = invoiceRepository.findAll();
        List<Invoice> invoices = new ArrayList<>();
        User user = (User) session.getAttribute("LOGIN_USER");
        for (Invoice invoice: listinvoices) {
            int ownerId = invoice.getRoomByRoomId().getRoomTypeByTypeId().getHostelByHostelId().getOwnerHostelId();
            if (ownerId == user.getUserId()) {
                invoices.add(invoice);
            }
        }
        mm.put("invoices", invoices);
        return "history";
    }

//    @RequestMapping(value = "/callBillByRoomId")
//    public RedirectView callBillByRoomId(ModelMap mm,
//                                         @RequestParam int roomId,
//                                         @RequestParam String invoiceName,
//                                         @RequestParam(required = false, defaultValue = "") String note,
//                                         HttpSession session,
//                                         RedirectAttributes attributes) {
//        User accSession = (User) session.getAttribute("LOGIN_USER");
//
//        if (accSession != null) {
//            //CHECK Authorization owner room
//            boolean ownerCheck = false;
//            int hostelId = 0;
//            try {
//                Room room = roomRepository.getRoomByRoomId(roomId);
//                RoomType roomType = roomTypeRepository.getRoomTypeByTypeId(room.getTypeId());
//                hostelId = roomType.getHostelId();
//                Hostel hostel = hostelRepository.getHostelByHostelId(hostelId);
//                int ownerId = hostel.getOwnerHostelId();
//                if (ownerId == accSession.getUserId()) {
//                    ownerCheck = true;
//                } else {
//                    attributes.addAttribute("message", "Not owner this hostel");
//                }
//            } catch (Exception ex) {
//                attributes.addAttribute("message", " Something error! " + ex.getMessage());
//            }
//
//            if (ownerCheck) {
//                List<RoomCharge> roomChargeList = roomChargeRepository.getAllByRoomIdAndInvoiceIdNull(roomId);
//                List<UsedService> usedServiceList = usedServiceRepository.findAllByInvoiceIdNullAndRoomId(roomId);
//                List<UsedUtility> usedUtilityList = usedUtilityRepository.findAllByInvoiceIdNullAndRoomId(roomId);
//
//                if (roomChargeList.isEmpty() && usedServiceList.isEmpty() && usedUtilityList.isEmpty()) {
//                    attributes.addAttribute("message", "Nothing to invoice");
//                    return new RedirectView ("/api/v1/host/");
//                } else {
//                    java.util.Date date = new Date();
//                    Timestamp ts = new Timestamp(date.getTime());
//
//                  //  Invoice invoice = new Invoice(roomId, invoiceName, (double) 0, true, note, ts, false,null);
//               //     invoiceRepository.save(invoice);
//
//                    double total = 0;
//
//                    for (RoomCharge roomCharge : roomChargeList) {
//                        total += roomCharge.getPrice();
//
//                        roomCharge.setInvoiceId(invoice.getInvoiceId());
//                        roomCharge = roomChargeRepository.save(roomCharge);
//                    }
//
//                    for (UsedService usedService : usedServiceList) {
//                        total += usedService.getPrice() * (double) usedService.getUsedQuantity();
//
//                        usedService.setInvoiceId(invoice.getInvoiceId());
//                        usedService = usedServiceRepository.save(usedService);
//                    }
//
//                    for (UsedUtility usedUtility : usedUtilityList) {
//                        total += usedUtility.getPricePerIndex() * (double) (usedUtility.getNewIndex() - usedUtility.getOldIndex());
//
//                        usedUtility.setInvoiceId(invoice.getInvoiceId());
//                        usedUtility = usedUtilityRepository.save(usedUtility);
//                    }
//
//                    invoice.setTotalAmount(total);
//                    invoice = invoiceRepository.save(invoice);
//
//                    attributes.addAttribute("message", "Invoice id: " + invoice.getInvoiceId());
//
//                    return new RedirectView ("/api/v1/invoice/");
//                }
//            } else {
//                return new RedirectView ("/api/v1/host/");
//            }
//        } else {
//            attributes.addAttribute("message", "Need login first");
//            return new RedirectView ("error");
//        }
//    }

}
