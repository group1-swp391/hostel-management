package com.example.hostelmanagement.controllers;



import com.example.hostelmanagement.entities.Booking;
import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


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

    @RequestMapping(value = "/{id}")
    public String bookingSite(@PathVariable("id") int id, ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            mm.put("fullName", accSession.getFullName());
            mm.put("email", accSession.getEmail());
            mm.put("phone", accSession.getPhone());

        }
        Room room = roomRepository.findById(id).get();
        mm.put("room", room);
        return "booking";
    }

    @PostMapping(value = "{roomId}/createBooking")
    public String createBooking(@PathVariable("roomId") int roomId,
                                @RequestParam java.sql.Date appointmentDate,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String phone,
                                HttpSession session,
                                ModelMap mm) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession != null) {
            Booking booking = Booking.builder()
                    .userId(accSession.getUserId())
                    .roomId(1)
                    .appointmentDate(appointmentDate)
                    .email(email)
                    .phone(phone)
                    .build();

            if (booking != null)
                mm.put("message", "Create success booking id : " + booking.getBookingId());
            return bookingSite(roomId,mm, session);
        }else {
            mm.put("message", "Need login first!");

            return "error";
        }
    }




}
