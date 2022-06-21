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
@RequestMapping(value = "api/v1/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping(value = "show-info")
    public String showInfo(HttpSession session) {
        if (session.getAttribute("LOGIN_USER")==null) {
            return login();
        }
        return "account";
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
                return "redirect:/api/v1/Admin/";
            }
            if (user.getRoleId()==2) {
                String n = "<i class=\"fa-solid fa-user-tie me-2\"></i>";
                session.setAttribute("n", n);
                return "redirect:/api/v1/host/";
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
        return "redirect:/";
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

    @PostMapping(value = "update")
    public String update(@RequestParam("userName") String userName, @RequestParam("fullName") String fullName,
                         @RequestParam("dateOfBirth") Date dateOfBirth, @RequestParam("gender") String gender,
                         @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("documentId") String documentId,
                         @RequestParam(value = "documentFrontSide", required = false) Part documentFrontSide, @RequestParam(value = "documentBackSide", required = false) Part documentBackSide,
                         HttpSession session, ModelMap mm) {
        User user = (User) session.getAttribute("LOGIN_USER");
        try {
            user.setUserName(userName);
            user.setFullName(fullName);
            user.setDateOfBirth(dateOfBirth);
            user.setGender("Male".equals(gender));
            user.setPhone(phone);
            user.setEmail(email);
            user.setDocumentId(documentId);
            if (documentFrontSide.getSize()>0) user.setDocumentFrontSide(Utils.getByteImage(documentFrontSide));
            if (documentBackSide.getSize()>0) user.setDocumentBackSide(Utils.getByteImage(documentBackSide));
            userRepository.save(user);
            mm.put("message", "Update successfully!");
        } catch (Exception e) {
            mm.put("message","Error while updating");
        }finally {
            return "redirect:show-info";
        }
    }

    @GetMapping(value = "change-password")
    public String changePassword() {
        return "changePassword";
    }

    @PostMapping(value = "change-password")
    public String changePassword(HttpSession session, @RequestParam("oldPassword") String oldPassword, @RequestParam("resetPassword") String resetPassword,
                                 @RequestParam("password") String password, ModelMap mm) {
        User user = (User) session.getAttribute("LOGIN_USER");
        if (resetPassword.equals(password) && oldPassword.equals(user.getPassword())) {
            user.setPassword(password);
            userRepository.save(user);
            mm.put("message", "Đổi mật khẩu thành công!");
            return "account";
        }
        mm.put("message", "Đổi mật khẩu thất bại!");
        return "changePassword";
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
