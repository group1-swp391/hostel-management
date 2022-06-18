package com.example.hostelmanagement.controllers.User;

import com.example.hostelmanagement.models.AccountSession;
import com.example.hostelmanagement.models.Booking;
import com.example.hostelmanagement.models.Contracts;
import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.BookingRepository;
import com.example.hostelmanagement.repositories.ContractRepository;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "api/v1/User")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BookingRepository bookingRepository;


    private List<User> users = new ArrayList<User>();

    @RequestMapping(value = "/showInfo")
    public String getUserInfo(HttpSession session, ModelMap mm) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            User user = userRepository.findById(accSession.getUserid()).get();
            mm.put("message", accSession.getUserid() + " " + "role: " + accSession.getRoleid() + " logtime: " + accSession.getLogtime());
            mm.put("user", user);

            return "/test/userinfo";
        }else {
            mm.put("message", "Need login first!");

            return "error";
        }
    }

    @RequestMapping(value = "/contract")
    public String getContract(HttpSession session, ModelMap mm) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            List<Contracts> contracts = contractRepository.getAllByUserId(accSession.getUserid());
            mm.put("message", accSession.getUserid() + " " + "role: " + accSession.getRoleid() + " logtime: " + accSession.getLogtime());
            mm.put("contracts", contracts);

            return "/test/contracts";
        }else {
            mm.put("message", "Need login first!");

            return "error";
        }
    }

    @RequestMapping(value = "/createBooking")
    public String createBooking(@RequestParam int roomId,
                                @RequestParam java.sql.Date appointmentDate,
                                @RequestParam java.sql.Date startDate,
                                @RequestParam java.sql.Date endDate,
                                HttpSession session,
                                ModelMap mm) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            Booking booking = new Booking(accSession.getUserid(), roomId, appointmentDate, startDate, endDate, false, true);
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
