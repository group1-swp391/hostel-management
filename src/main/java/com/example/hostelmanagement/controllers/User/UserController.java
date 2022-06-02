package com.example.hostelmanagement.controllers.User;

import com.example.hostelmanagement.models.AccountSession;
import com.example.hostelmanagement.models.Contracts;
import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.ContractRepository;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "api/v1/User")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractRepository contractRepository;

    private List<User> users = new ArrayList<User>();

    @RequestMapping(value = "/info")
    public String getUserInfo(@ModelAttribute(value = "user") User user, HttpSession session, ModelMap mm) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            user = userRepository.findUserByUserId(accSession.getUserid());
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





}
