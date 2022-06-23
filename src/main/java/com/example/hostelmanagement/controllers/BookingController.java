package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.Booking;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "api/v1/booking/")
public class BookingController {
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
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping(value = "createBooking")
    public String createBooking(@RequestParam int roomId,
                                @RequestParam java.sql.Date appointmentDate,
                                @RequestParam String email,
                                @RequestParam String phone,
                                HttpSession session,
                                ModelMap mm) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            Booking booking = Booking.builder()
                    .userId(accSession.getUserId())
                    .roomId(roomId)
                    .appointmentDate(appointmentDate)
                    .email(email)
                    .phone(phone)
                    .build();
            booking = bookingRepository.save(booking);
            if (booking != null)
                mm.put("message", "Create success booking id : " + booking.getBookingId());
            return "/booking";
        }else {
            mm.put("message", "Need login first!");

            return "error";
        }
    }


}
