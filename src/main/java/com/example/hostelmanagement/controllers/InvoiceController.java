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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
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

    @RequestMapping(value = "detail-invoice")
    public String invoiceDetail() {
        return "detailHistory";
    }

    @RequestMapping(value = "")
    public String getInvoiceSite(ModelMap mm, HttpSession session) {
        return getAllInvoices(mm, session);
    }

    @RequestMapping(value = "/{invoiceId}")
    public String getInvoiceDetails(
            @PathVariable(value="invoiceId") int invoiceId,
            ModelMap mm,
            HttpSession session) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + invoiceId));

        Collection<RoomCharge> roomCharges = invoice.getRoomChargesByInvoiceId();
        Collection<UsedService> usedServices = invoice.getUsedServicesByInvoiceId();
        Collection<UsedUtility> usedUtilities = invoice.getTblUsedUtilitiesByInvoiceId();

        mm.put("invoice", invoice);
        mm.put("roomCharges", roomCharges);
        mm.put("usedServices", usedServices);
        mm.put("usedUtilities", usedUtilities);

        return "invoice";
    }

    @RequestMapping(value = "getAllInvoice")
    public String getAllInvoices(ModelMap mm, HttpSession session) {
        List<Invoice> listinvoices = invoiceRepository.findAll();
        List<Invoice> invoices = new ArrayList<>();
        try {
            User accSession = (User) session.getAttribute("LOGIN_USER");
            for (Invoice invoice: listinvoices) {
                Room room = roomRepository.getRoomByRoomId(invoice.getRoomId());
                if (room != null) {
                    Hostel hostel = hostelRepository.getHostelByHostelId(room.getRoomTypeByTypeId().getHostelId());
                    if (accSession.getUserId() ==  hostel.getOwnerHostelId()) {
                        invoices.add(invoice);
                    }
                }
            }
        } catch (Exception ex) {

        }
        mm.put("invoices", invoices);
        return "history";
    }

    @RequestMapping(value = "/callBillByRoomId")
    public RedirectView callBillByRoomId(ModelMap mm,
                                         @RequestParam int roomId,
                                         @RequestParam String invoiceName,
                                         @RequestParam(required = false, defaultValue = "") String note,
                                         HttpSession session,
                                         RedirectAttributes attributes) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            //CHECK Authorization owner room
            int owner = accSession.getUserId();
            Room room = roomRepository.findById(roomId)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + roomId));

            if (owner == room.getRoomTypeByTypeId().getHostelByHostelId().getOwnerHostelId()) {
                List<RoomCharge> roomChargeList = roomChargeRepository.getAllByRoomIdAndInvoiceIdNull(roomId);
                List<UsedService> usedServiceList = usedServiceRepository.findAllByInvoiceIdNullAndRoomId(roomId);
                List<UsedUtility> usedUtilityList = usedUtilityRepository.findAllByInvoiceIdNullAndRoomId(roomId);

                if (roomChargeList.isEmpty() && usedServiceList.isEmpty() && usedUtilityList.isEmpty()) {
                    attributes.addAttribute("message", "Nothing to invoice");
                    return new RedirectView ("/api/v1/host/");
                } else {
                    java.util.Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());


                    Invoice invoice = Invoice.builder()
                            .roomId(roomId)
                            .invoiceName(invoiceName)
                            .totalAmount((double) 0)
                            .invoiceStatus(true)
                            .paymentStatus(false)
                            .note(note)
                            .invoiceCreateDate(ts)
                            .build();
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

                    attributes.addAttribute("message", "Invoice id: " + invoice.getInvoiceId());

                    return new RedirectView ("/api/v1/invoice/");
                }
            } else {
                return new RedirectView ("/api/v1/host/");
            }
        } else {
            attributes.addAttribute("message", "Need login first");
            return new RedirectView ("error");
        }
    }


    @RequestMapping(value = "/callBillAllRoomByHostelId")
    public RedirectView callBillByHostelId(ModelMap mm,
                                           @RequestParam int hostelId,
                                           @RequestParam String invoiceName,
                                           @RequestParam(required = false, defaultValue = "") String note,
                                           HttpSession session,
                                           RedirectAttributes attributes) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            //CHECK Authorization owner room
            int owner = accSession.getUserId();

            Hostel hostel = hostelRepository.findById(hostelId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid hostel Id:" + hostelId));

            if (owner == hostel.getOwnerHostelId()) {

                Collection<RoomType> roomTypes = hostel.getRoomTypesByHostelId();
                List<Room> rooms = new ArrayList<>();
                for (RoomType roomType : roomTypes) {
                    Collection<Room> roomCollection = roomType.getRoomsByTypeId();
                    for (Room room : roomCollection) {
                        rooms.add(room);
                    }

                }
                int countInvoice = 0;
                for (Room room : rooms) {
                    List<RoomCharge> roomChargeList = roomChargeRepository.getAllByRoomIdAndInvoiceIdNull(room.getRoomId());
                    List<UsedService> usedServiceList = usedServiceRepository.findAllByInvoiceIdNullAndRoomId(room.getRoomId());
                    List<UsedUtility> usedUtilityList = usedUtilityRepository.findAllByInvoiceIdNullAndRoomId(room.getRoomId());

                    if (roomChargeList.isEmpty() && usedServiceList.isEmpty() && usedUtilityList.isEmpty()) {
                        attributes.addAttribute("message", "Nothing to invoice");
                        return new RedirectView("/api/v1/host/");
                    } else {
                        java.util.Date date = new Date();
                        Timestamp ts = new Timestamp(date.getTime());


                        Invoice invoice = Invoice.builder()
                                .roomId(room.getRoomId())
                                .invoiceName(invoiceName)
                                .totalAmount((double) 0)
                                .invoiceStatus(true)
                                .paymentStatus(false)
                                .note(note)
                                .invoiceCreateDate(ts)
                                .build();
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
                        if (invoice != null)
                            countInvoice++;
                    }

                    attributes.addAttribute("message", "Create success " + countInvoice + " invoice! to hostel id" + hostelId);

                    return new RedirectView("/api/v1/invoice/");
                }
            } else {
                return new RedirectView("/api/v1/host/");
            }
        } else {
            attributes.addAttribute("message", "Need login first");
            return new RedirectView("error");
        }
        return new RedirectView("error");

    }








    @RequestMapping(value="test2")
    public String test11(ModelMap mm) {
        Invoice invoice = invoiceRepository.findById(3).get();

        mm.put("invoice", invoice);



        return "money";
    }

}
