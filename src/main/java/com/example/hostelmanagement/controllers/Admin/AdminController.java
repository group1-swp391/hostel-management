package com.example.hostelmanagement.controllers.Admin;

import com.example.hostelmanagement.models.*;
import com.example.hostelmanagement.repositories.*;
import javafx.animation.KeyFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    @RequestMapping(value = "/getAllUser")
    public String getAllUsers(ModelMap mm) {

        List<User> listUsers = userRepository.findAllByUserStatusTrue();
        mm.put("listUsers", listUsers);

        return "admin/user/listusers";
    }

    @RequestMapping(value = "/getUser/{userid}")
    public String getUser(@PathVariable("userid") int userid,
                                    ModelMap mm) {
        Optional<User> userOptional = userRepository.findById(userid);

        if (userOptional != null && userOptional.get().getUserStatus()) {
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
        if (user != null && user.getUserStatus()) {
            user.setUserStatus(false);
            user = userRepository.save(user);
            if(!user.getUserStatus())
                mm.put("message", "Delete Success Contract ID: " + user.getUserId());
        } else {
            mm.put("message", "Not found user id");
        }

        String getAllContract = getAllContract(mm);
        return getAllContract;
    }

    @RequestMapping(value = "/getAllContract")
    public String getAllContract(ModelMap mm) {

        List<Contracts> contracts = contractRepository.findAllByContractStatusTrue();
        mm.put("listContracts", contracts);

        return "admin/contract/listcontracts";
    }

    @PostMapping(value = "/createContract")
    public String createContract(@RequestParam("startDate") java.sql.Date startDate,
                                 @RequestParam("endDate") java.sql.Date endDate,
                                 @RequestParam("userId") int userId,
                                 @RequestParam("roomId") int roomId,
                                 ModelMap mm) {

        java.util.Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Room> roomOptional = roomRepository.findById(roomId);

        if (!userOptional.isPresent() || !userOptional.get().getUserStatus()) {
            mm.put("message", "Not found userid!");
        } else if (!roomOptional.isPresent() || !userOptional.get().getUserStatus()) {
            mm.put("message", "Not found roomid!");
        } else if(startDate.after(endDate)) {
            mm.put("message", "Start date must before end date");
        }else {
            Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(roomOptional.get().getTypeId());
            if(roomTypeOptional.isPresent() && roomTypeOptional.get().getRoomTypeStatus()) {
                RoomType roomType = roomTypeOptional.get();

                try {
                    double deposit = roomType.getDepositPrice();

                    Contracts contract = new Contracts(startDate, endDate, deposit, userId, roomId, true, ts, null);
                    contract = contractRepository.save(contract);

                    mm.put("message",  "Create success contract id: " + contract.getContractId());
                } catch (Exception e) {
                    mm.put("message", "Create contract error!");
                }

            } else {
                mm.put("message", "Not found room typeid!");
            }
        }

        return getAllContract(mm);
    }

    @RequestMapping(value = "/deleteContract")
    public String deleteContract(@RequestParam("contractid") int contractid, ModelMap mm) {

        Contracts contract = contractRepository.findById(contractid).get();
        if (contract != null && contract.getContractStatus()) {
            contract.setContractStatus(false);
            contract = contractRepository.save(contract);
            if(!contract.getContractStatus())
                mm.put("message", "Delete Success Contract ID: " + contract.getContractId());
        } else {
            mm.put("message", "Not found contract id");
        }

        String getAllContract = getAllContract(mm);
        return getAllContract;
    }

    @RequestMapping(value = "/liquidationContract")
    public String liquidationContract(@RequestParam("contractid") int contractid, ModelMap mm) {

        Contracts contract = contractRepository.findById(contractid).get();

        if (contract != null && contract.getContractStatus() && contract.getContractLiquidationTime() == null) {

            java.util.Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            contract.setContractLiquidationTime(ts);
            contract = contractRepository.save(contract);
            if(contract.getContractLiquidationTime() != null)
                mm.put("message", "Liquidation Success Contract ID: " + contract.getContractId());
        } else {
            mm.put("message", "Not found contract id or contract was liquidation");
        }

        String getAllContract = getAllContract(mm);
        return getAllContract;
    }

    @RequestMapping(value = "/getContract/{contractid}")
    public String getUpdateContract(@PathVariable("contractid") int contractid,
                                    ModelMap mm) {
        try {
            Contracts contract = contractRepository.findById(contractid).get();
            if (contract.getContractStatus()) {

            mm.put("contract", contract);

            } else {
                mm.put("message", "Not found contract id  " + contractid);
            }
        } catch(Exception e) {
            mm.put("message", "Not found contract id " + contractid);
        }

        return getAllContract(mm);
    }

    @PostMapping(value = "/updateContract/{contractid}")
    public String updateContract(@PathVariable("contractid") int contractid,
                                    @RequestParam java.sql.Date startDate,
                                    @RequestParam java.sql.Date endDate,
                                    @RequestParam float deposit,
                                    @RequestParam int userId,
                                    @RequestParam int roomId
                                    ,ModelMap mm) {

            if(!contractRepository.existsById(contractid)) {
                mm.put("message", "Not found contract id " + contractid);
            } else if(!userRepository.existsById(userId)) {
                mm.put("message", "Not found user id " + userId);
            } else if (!roomRepository.existsById(roomId)) {
                mm.put("message", "Not found room id" + roomId);
            } else if (startDate.after(endDate)) {
                mm.put("message", "Start date must before end date");
            } else {
                Contracts contract = contractRepository.findById(contractid).get();

                contract.setStartDate(startDate);
                contract.setEndDate(endDate);
                contract.getDeposit();
                contract.setUserId(userId);
                contract.setRoomId(roomId);

                contractRepository.save(contract);
                mm.put("message", "Update success contract id " + contractid);
            }

            String getAllContract = getAllContract(mm);
            return getAllContract;
    }



    @RequestMapping(value = "/getAllHostel")
    public String getAllHostel(ModelMap mm) {

        List<Hostel> hostels = hostelRepository.getAllByHostelStatusTrue();
        mm.put("listHostels", hostels);

        return "admin/hostel/listhostels";
    }

    @RequestMapping(value = "/getHostel/{hostelid}")
    public String getHostel(@PathVariable("hostelid") int hostelid,
                          ModelMap mm) {

        if (hostelRepository.existsById(hostelid) && hostelRepository.findById(hostelid).get().getHostelStatus()) {
            Hostel hostel = hostelRepository.findById(hostelid).get();
            mm.put("hostel", hostel);
        } else {
            mm.put("message", "Not found hostel id " + hostelid);
        }

        return getAllHostel(mm);
    }

    @PostMapping(value = "/createHostel")
    public String createHostel(@RequestParam int ownerHostelId,
                               @RequestParam String address,
                               @RequestParam String hostelName,
                               @RequestParam String imageHostel,
                               ModelMap mm) {


        Optional<User> userOptional = userRepository.findById(ownerHostelId);

        if (!userOptional.isPresent() || !userOptional.get().getUserStatus() || userOptional.get().getRoleId() != 2) {
            mm.put("message", "Not found userid or user role not hostel owner!");
        } else {
                try {
                    Hostel hostel = new Hostel(ownerHostelId, address, hostelName, true, imageHostel);

                    hostel = hostelRepository.save(hostel);

                    mm.put("message",  "Create success hostel id: " + hostel.getHostelId());
                } catch (Exception e) {
                    mm.put("message", "Create hostel error!");
                }
        }

        return getAllHostel(mm);
    }

    @PostMapping(value = "/updateHostel/{hostelid}")
    public String updateHostel(@PathVariable("hostelid") int hostelid,
                               @RequestParam int ownerHostelId,
                               @RequestParam String address,
                               @RequestParam String hostelName,
                               @RequestParam String imageHostel,
                               ModelMap mm) {


        Optional<User> userOptional = userRepository.findById(ownerHostelId);

        if (!userOptional.isPresent() || !userOptional.get().getUserStatus() || userOptional.get().getRoleId() != 2) {
            mm.put("message", "Not found userid or user role not hostel owner!");
        } else if (!hostelRepository.existsById(hostelid) || !hostelRepository.findById(hostelid).get().getHostelStatus()) {
            mm.put("message", "Not found hostel id");
        }
        else {
            try {

                Hostel hostel = hostelRepository.findById(hostelid).get();
                hostel.setOwnerHostelId(ownerHostelId);
                hostel.setHostelName(hostelName);
                hostel.setAddress(address);
                hostel.setImageHostel(imageHostel);

                hostel = hostelRepository.save(hostel);

                mm.put("message",  "Update success hostel id: " + hostel.getHostelId());
            } catch (Exception e) {
                mm.put("message", "Update hostel error!");
            }
        }

        return getAllHostel(mm);
    }

    @RequestMapping(value = "/deleteHostel")
    public String deleteHostel(@RequestParam("hostelid") int hostelid, ModelMap mm) {

        Hostel hostel = hostelRepository.findById(hostelid).get();
        if (hostel != null && hostel.getHostelStatus()) {
            hostel.setHostelStatus(false);
            hostel = hostelRepository.save(hostel);
            if(!hostel.getHostelStatus())
                mm.put("message", "Delete Success Hostel ID: " + hostel.getHostelId());
        } else {
            mm.put("message", "Not found hostel id");
        }

        return getAllHostel(mm);
    }

}


