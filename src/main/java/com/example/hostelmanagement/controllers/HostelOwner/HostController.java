package com.example.hostelmanagement.controllers.HostelOwner;

import com.example.hostelmanagement.models.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "api/v1/Host")
public class HostController {
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
    private InvoiceRepository invoiceRepository;


    @RequestMapping(value = "/callBillByRoomId")
    public String callBillByRoomId(ModelMap mm,
                                   @RequestParam int roomId,
                                   @RequestParam String invoiceName,
                                   @RequestParam(required = false, defaultValue = "") String note,
                                   HttpSession session) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            //CHECK Authorization owner room
            //...
            List<RoomCharge> roomChargeList = roomChargeRepository.getAllByRoomIdAndInvoiceIdNull(roomId);
            List<UsedService> usedServiceList = usedServiceRepository.findAllByInvoiceIdNullAndRoomId(roomId);
            List<UsedUtility> usedUtilityList = usedUtilityRepository.findAllByInvoiceIdNullAndRoomId(roomId);

            if(roomChargeList.isEmpty() && usedServiceList.isEmpty() && usedUtilityList.isEmpty()) {
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
            mm.put("message", "Need login first");
            return "test/login";
        }
    }



}
