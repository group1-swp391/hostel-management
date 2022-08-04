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
    @RequestMapping(value = "show-info")
    public String showInfo(HttpSession session, ModelMap mm) {
        if (session.getAttribute("LOGIN_USER")==null) {
            return login();
        }
        User user = (User) session.getAttribute("LOGIN_USER");
        mm.put("userLogin", user);
        return "userdetail";
    }


    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login/auth")
    public String login(@RequestParam String userName, @RequestParam String password, HttpSession session, ModelMap mm) {
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
            String n = "<img src=\"/img/person.png\" width=\"40px\" height=\"40px\" />";
            session.setAttribute("n", n);
            return "index";
        } else {
            mm.put("message", "Incorrect username or password");

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

    @PostMapping(value = "register")
    public String register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("confirm") String confirm,
                           @RequestParam("fullName") String fullName, @RequestParam("gender") boolean gender,
                           @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("documentId") String documentId,
                           @RequestParam("dateOfBirth") Date dateOfBirth, ModelMap mm) throws IOException {
        try {
            if (password.equals(confirm)) {
                userRepository.save(User.builder().userName(userName).password(password).fullName(fullName)
                        .gender(gender).phone(phone).email(email).documentId(documentId).dateOfBirth(dateOfBirth).roleId(3).userStatus(true).build());
                mm.put("message", "Đăng kí thành công!");
            }
            return "login";
        }catch (Exception e) {
            mm.put("message", "Đăng kí thất bại!");
            return "register";
        }

    }

    @PostMapping(value = "update")
    public String update(@RequestParam String userName, @RequestParam String fullName,
                         @RequestParam Date dateOfBirth, @RequestParam String gender,
                         @RequestParam String phone, @RequestParam String email, @RequestParam String documentId,
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
//            if (documentFrontSide.getSize()>0) user.setDocumentFrontSide(Utils.getByteImage(documentFrontSide));
//            if (documentBackSide.getSize()>0) user.setDocumentBackSide(Utils.getByteImage(documentBackSide));
            userRepository.save(user);
            mm.put("message", "Cập nhật thông tin thành công");
        } catch (Exception e) {
            mm.put("message","Cập nhật thông tin thất bại");
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
