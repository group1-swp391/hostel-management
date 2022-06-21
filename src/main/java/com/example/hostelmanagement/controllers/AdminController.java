package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/Admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @RequestMapping(value = "")
    public String getAdminHome(ModelMap mm) {


        return getAllUsers(mm);
    }

    @RequestMapping(value = "/getAllUser")
    public String getAllUsers(ModelMap mm) {

        List<User> listUsers = userRepository.findAllByUserStatusTrue();
        mm.put("listUsers", listUsers);

        return "admin";
    }

    @RequestMapping(value = "/getUser/{userid}")
    public String getUser(@PathVariable("userid") int userid,
                                    ModelMap mm) {
        Optional<User> userOptional = userRepository.findById(userid);

        if (userOptional != null && userOptional.get().isUserStatus()) {
            User user = userOptional.get();
            mm.put("user", user);
        } else {
            mm.put("message", "Not found user id " + userid);
        }

        return getAllUsers(mm);
    }

    @PostMapping(value = "/updateUser/{userid}")
    public String updateUser(@PathVariable("userid") int userid,
                             @RequestParam String userName,
                             @RequestParam String password,
                             @RequestParam String fullName,
                             @RequestParam java.sql.Date dateOfBirth,
                             @RequestParam boolean gender,
                             @RequestParam String phone,
                             @RequestParam String email,
                             @RequestParam(required = false) String documentId,
                             @RequestParam int roleId,
                             ModelMap mm) {
        if(!userRepository.existsById(userid)) {
            mm.put("message", "Not found user id " + userid);
        } else {
            User user = userRepository.findById(userid).get();

            user.setUserName(userName);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setDateOfBirth(dateOfBirth);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setDocumentId(documentId);
            user.setRoleId(roleId);

            user = userRepository.save(user);

            if (user != null)
                mm.put("message", "Update success user id " + user.getUserId());
            else
                mm.put("message", "Have error when update");
        }

        return getAllUsers(mm);
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userid") int userid, ModelMap mm) {

        User user = userRepository.findById(userid).get();
        if (user != null && user.isUserStatus()) {
            user.setUserStatus(false);
            user = userRepository.save(user);
            if(!user.isUserStatus())
                mm.put("message", "Delete Success Contract ID: " + user.getUserId());
        } else {
            mm.put("message", "Not found user id");
        }

        return getAllUsers(mm);
    }


}


