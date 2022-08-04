package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Utils;
import com.example.hostelmanagement.utils.WriteChartUtils;
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
import java.time.Year;
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
    public String callBillByRoomId(ModelMap mm,
                                         @RequestParam int roomId,
                                         @RequestParam String invoiceName,
                                         @RequestParam(required = false, defaultValue = "") String note,
                                         HttpSession session, RedirectAttributes redirectAttributes,
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
                    redirectAttributes.addFlashAttribute("flashAttr","Không có dịch vụ/tiện ích/tiền phòng chưa xuất hoá đơn!");
                    return "redirect:/api/v1/room/"+roomId;
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

                    redirectAttributes.addFlashAttribute("flashAttr","Xuất hoá đơn thành công!");
                    return "redirect:/api/v1/room/"+roomId;
                }
            } else {
                return "redirect:/api/v1/room/"+roomId;
            }
        } else {
            return "redirect:/api/v1/room/"+roomId;
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

    @RequestMapping(value = "money")
    public String moneySite(ModelMap mm, HttpSession session, @RequestParam(required = false) Optional<Integer> yearOptional) {
        User loginUser = (User)session.getAttribute("LOGIN_USER");
        Calendar cal = Calendar.getInstance();
        Set<Integer> listYears = new HashSet<>();
        int year;

        if (yearOptional.isPresent()) {
            year = yearOptional.get();
        } else {
            year = cal.get(Calendar.YEAR);
        }
        WriteChartUtils.getHostelList(hostelRepository, loginUser);
        mm.put("invoicesPrice", WriteChartUtils.getPricePerMonth(listYears, year, cal));
        mm.put("amountOfHostel", WriteChartUtils.getPriceOfEachHostel());
        mm.put("usersInHostel", WriteChartUtils.getTotalUserInHostel());

        mm.put("totalHostel", hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(loginUser.getUserId()).size());
        mm.put("totalRoomStatus", WriteChartUtils.getTotalRoomStatus());
        mm.put("listYear", listYears);

        List<Room> rooms = new ArrayList<>();
        hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(loginUser.getUserId()).forEach(
                h -> h.getRoomTypesByHostelId().forEach(rt -> rt.getRoomsByTypeId().forEach(r -> rooms.add(r)))
        );
        mm.put("totalRooms", rooms.size());
        return "money";
    }
    @RequestMapping(value = "historyinvoice")
    public String historyInvoiceSite(ModelMap mm, HttpSession session) {return getAllHistoryInvoice(mm,session);}

    @RequestMapping(value = "getAllHistoryInvoice")
    public String getAllHistoryInvoice(ModelMap mm, HttpSession session){
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "login";
        }


        User user = userRepository.findOneByUserId(accSession.getUserId());

        Collection<Invoice> invoicesByIdUser = user.getInvoicesByUserId();

        mm.put("invoices",invoicesByIdUser);
        mm.put("user",user);
        return "historybooking";
    }



    @RequestMapping(value = "viewinvoicedetail")
    public String getDetailInvoiceSite(ModelMap mm,HttpSession session,@ModelAttribute("invoiceID") int invoiceid){return getDetailInvoiceBy(mm,session,invoiceid);}

    @RequestMapping(value = "getDetailInvoiceBy")
    public String getDetailInvoiceBy( ModelMap mm, HttpSession session,int invoiceID){
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "login";
        }
        Optional<Invoice> inv = invoiceRepository.findById(invoiceID);
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<Invoice> invoicesByIdUser = new ArrayList<>();
        List<RoomCharge> roomchargeList = roomChargeRepository.findAll();
        List<RoomCharge> roomchargebyinvoiceID = new ArrayList<>();
        List<UsedUtility> usedUtilityList = usedUtilityRepository.findAll();
        List<UsedUtility> usedUtilityByInvoiceID = new ArrayList<>();
        List<UsedService> usedServiceList= usedServiceRepository.findAll();
        List<UsedService> usedServiceByInvoiceID = new ArrayList<>();



        for(RoomCharge r:roomchargeList ){
            if(r.getInvoiceId() == invoiceID){
                roomchargebyinvoiceID.add(r);
            }
        }
        for(UsedUtility uu:usedUtilityList ){
            if(uu.getInvoiceId() == invoiceID){
                usedUtilityByInvoiceID.add(uu);
//                        uu.getUtilityTypeByUtilityTypeId().getPricePerIndex()
            }
        }
        for(UsedService us:usedServiceList ){
            if(us.getInvoiceId() == invoiceID){
                usedServiceByInvoiceID.add(us);
//                        us.getUsedQuantity()
            }
        }

        mm.put("invoicedetail",inv.get());
        mm.put("moneyroom",roomchargebyinvoiceID);
        mm.put("utiliti",usedUtilityByInvoiceID);
        mm.put("service",usedServiceByInvoiceID);
        return "invoiceuserdetail";
    }

}
