package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"api/v1/contract/","api/v1/hometro/"})
public class ContractController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @RequestMapping(value = {"","contract"})
    public String getContractPage(ModelMap mm, HttpSession session) {
        return getAllContract(mm,session);
    }

    @RequestMapping(value = "getAllContract")
    public String getAllContract(ModelMap mm, HttpSession session) {

        List<Contracts> contracts = contractRepository.findAllByContractStatusTrue();

        HashMap<Contracts, User> contractuserHashMap = new HashMap<Contracts, User>();

        for (Contracts contract:contracts) {
            User user = userRepository.findById(contract.getUserId()).get();
            try {
                User accSession = (User) session.getAttribute("LOGIN_USER");
                Room room = roomRepository.getRoomByRoomId(contract.getRoomId());
                if (room != null) {
                    Hostel hostel = hostelRepository.getHostelByHostelId(room.getHostelId());
                    if (accSession.getUserId() ==  hostel.getOwnerHostelId()) {
                        contractuserHashMap.put(contract, user);
                    }
                }
            }
            catch (Exception ex) {

            }
        }

        mm.put("contractuserHashMap", contractuserHashMap);

        mm.put("listContracts", contracts);

        return "contract";
    }

    @PostMapping(value = "createContract")
    public String createContract(@RequestParam("startDate") java.sql.Date startDate,
                                 @RequestParam("endDate") java.sql.Date endDate,
                                 @RequestParam("userId") int userId,
                                 @RequestParam("roomId") int roomId,
                                 ModelMap mm, HttpSession session) {

        java.util.Date date = new java.util.Date();
        Timestamp ts = new Timestamp(date.getTime());

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Room> roomOptional = roomRepository.findById(roomId);

        if (!userOptional.isPresent() || !userOptional.get().isUserStatus()) {
            mm.put("message", "Not found userid!");
        } else if (!roomOptional.isPresent() || !userOptional.get().isUserStatus()) {
            mm.put("message", "Not found roomid!");
        } else if(startDate.after(endDate)) {
            mm.put("message", "Start date must before end date");
        }else {
            Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(roomOptional.get().getTypeId());
            if(roomTypeOptional.isPresent() && roomTypeOptional.get().isRoomTypeStatus()) {
                RoomType roomType = roomTypeOptional.get();

                try {
                    double deposit = roomType.getDepositPrice();

                    Contracts contract = new Contracts(startDate, endDate, deposit, userId, roomId, true, null, null, ts);
                    contract = contractRepository.save(contract);

                    mm.put("message",  "Create success contract id: " + contract.getContractId());
                } catch (Exception e) {
                    mm.put("message", "Create contract error!");
                }

            } else {
                mm.put("message", "Not found room typeid!");
            }
        }

        return getAllContract(mm, session);
    }

    @RequestMapping(value = "deleteContract")
    public String deleteContract(@RequestParam("contractid") int contractid, ModelMap mm, HttpSession session) {

        Contracts contract = contractRepository.findById(contractid).get();
        if (contract != null && contract.isContractStatus()) {
            contract.setContractStatus(false);
            contract = contractRepository.save(contract);
            if(!contract.isContractStatus())
                mm.put("message", "Delete Success Contract ID: " + contract.getContractId());
        } else {
            mm.put("message", "Not found contract id");
        }

        String getAllContract = getAllContract(mm, session);
        return getAllContract;
    }

    @RequestMapping(value = "liquidationContract")
    public String liquidationContract(@RequestParam("contractid") int contractid, ModelMap mm, HttpSession session) {

        Contracts contract = contractRepository.findById(contractid).get();

        if (contract != null && contract.isContractStatus() && contract.getContractLiquidationTime() == null) {

            java.util.Date date = new java.util.Date();
            Timestamp ts = new Timestamp(date.getTime());

            contract.setContractLiquidationTime(ts);
            contract = contractRepository.save(contract);
            if(contract.getContractLiquidationTime() != null)
                mm.put("message", "Liquidation Success Contract ID: " + contract.getContractId());
        } else {
            mm.put("message", "Not found contract id or contract was liquidation");
        }

        String getAllContract = getAllContract(mm, session);
        return getAllContract;
    }

    @RequestMapping(value = "getContract/{contractid}")
    public String getUpdateContract(@PathVariable("contractid") int contractid,
                                    ModelMap mm, HttpSession session) {
        try {
            Contracts contract = contractRepository.findById(contractid).get();
            if (contract.isContractStatus()) {


                mm.put("contract", contract);
            } else {
                mm.put("message", "Not found contract id  " + contractid);
            }
        } catch(Exception e) {
            mm.put("message", "Not found contract id " + contractid);
        }

        return getAllContract(mm, session);
    }

    @PostMapping(value = "updateContract/{contractid}")
    public String updateContract(@PathVariable("contractid") int contractid,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam float deposit,
                                 @RequestParam int userId,
                                 @RequestParam int roomId
            ,ModelMap mm, HttpSession session) {

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

        String getAllContract = getAllContract(mm, session);
        return getAllContract;
    }





}