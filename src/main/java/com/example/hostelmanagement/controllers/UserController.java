package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.UserRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "api/v1/User/")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping(value = "showInfo")
    public String showInfo(HttpSession session) {
        if (session.getAttribute("LOGIN_USER")==null) {
            return "login";
        }
        return "userinfo";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(@RequestParam(name = "userName",required = false) String userName, @RequestParam(name = "password",required = false) String password, HttpSession session, ModelMap mm) {
        User user = userRepository.getUserByUserNameAndPasswordAndUserStatus(userName, password, true);
        if(user!=null) {
            session.setAttribute("LOGIN_USER", user);
            if (user.getRoleId()==1) {
                return "admin_userMngt";
            }
            if (user.getRoleId()==2) {
                return "host_hostelMngt";
            }
            return "index";
        } else {
            mm.put("message", "Invalid account");
            return "login";
        }
    }
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @RequestMapping(value = "register")
    public String register() {
        return "register";
    }
//    @PostMapping(value = "register")
//    public String register(@ModelAttribute(value = "user") User user, ModelMap mm) {
//        if (!user.getUserName().equals("long")) {
//            mm.put("error", "Register failed");
//            return "register";
//        }
//        mm.put("message", user.getUserName());
//        return "welcome";
//    }

    @PostMapping(value = "register")
    public String register(@RequestParam("userName") String userName, @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword, @RequestParam("fullName") String fullName,
                           @RequestParam("dateOfBirth") Date dateOfBirth, @RequestParam("gender") String gender,
                           @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("documentId") String documentId,
                           @RequestParam("documentFrontSide") Part documentFrontSide, @RequestParam("documentBackSide") Part documentBackSide,
                           @RequestParam("roleId") int roleId, ModelMap mm) throws IOException {
        if (!password.equals(confirmPassword)) {
            mm.put("error", "Register failed");
            return "register";
        }
        userRepository.save(new User(userName, password, fullName, dateOfBirth,"Male".equals(gender),phone, email,documentId, Utils.getByteImage(documentFrontSide), Utils.getByteImage(documentBackSide), roleId, true, new Date(System.currentTimeMillis())));
        return "login";
    }

    @GetMapping(value = "search")
    public String searchName(@RequestParam(name = "userName",required = false) String userName, ModelMap mm) {
        try {
            mm.put("userName", userName);
            List<User> users = userRepository.getAllByUserNameContains(userName);
            if (users.isEmpty()) {
                mm.put("message", "No result");
            }else {
                mm.put("message","Total result: "+users.size());
                mm.put("users", users);
            }
        } catch (Exception e) {
            System.out.println("error");
        } finally {
            return "admin_userMngt";
        }
    }
    @ResponseBody
    @GetMapping("imageFront/{id}")
    public ResponseEntity<byte[]> getFrontDocument(@PathVariable("id") int id) {
        Optional<User> user = userRepository.findById(id);
        byte[] imageBytes = null;
        if (user.isPresent()) {

            imageBytes = user.get().getDocumentFrontSide();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    @ResponseBody
    @GetMapping("imageBack/{id}")
    public ResponseEntity<byte[]> getBackDocument(@PathVariable("id") int id) {
        Optional<User> user = userRepository.findById(id);
        byte[] imageBytes = null;
        if (user.isPresent()) {

            imageBytes = user.get().getDocumentBackSide();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
