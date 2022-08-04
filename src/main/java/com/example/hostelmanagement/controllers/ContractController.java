package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
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

        mm.put("contracts", contracts);

        return "contract";
    }



    @PostMapping(value = "createContract")
    public String createContract(@RequestParam("startDate") java.sql.Date startDate,
                                 @RequestParam("endDate") java.sql.Date endDate,
                                 @RequestParam("userInfo") String userInfo,
                                 @RequestParam("roomId") int roomId,
                                 @RequestParam("deposit") double deposit,
                                 @RequestParam("depositPaymentStatus") boolean depositPaymentStatus,
                                 ModelMap mm, HttpSession session) {

        java.util.Date date = new java.util.Date();
        Timestamp ts = new Timestamp(date.getTime());
        int userId = 0;
        User user = userRepository.findUserByEmailIgnoreCaseOrPhoneIgnoreCaseOrDocumentIdIgnoreCase(userInfo, userInfo, userInfo);
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        userId = user.getUserId();
        if (userId == 0) {
            mm.put("message", "Không tìm thấy người dùng!");
        } else if (!roomOptional.isPresent()) {
            mm.put("message", "Không tìm thấy phòng!");
        } else if(startDate.after(endDate)) {
            mm.put("message", "Ngày bắt đầu phải sau ngày kết thúc!");
        }else {
            Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(roomOptional.get().getTypeId());
            if(roomTypeOptional.isPresent() && roomTypeOptional.get().isRoomTypeStatus()) {
                RoomType roomType = roomTypeOptional.get();

                try {
                    //Contracts contract = new Contracts(startDate, endDate, deposit, userId, roomId, true, null, null, ts);

                    Contracts contract = Contracts.builder()
                            .startDate(startDate)
                            .endDate(endDate)
                            .deposit(deposit)
                            .userId(userId)
                            .roomId(roomId)
                            .contractStatus(false)
                            .depositPaymentStatus(depositPaymentStatus)
                            .createContractTime(ts)
                            .build();
                    contract = contractRepository.save(contract);

                    mm.put("message",  "Tạo thành công hợp đồng" + contract.getContractId());
                } catch (Exception e) {
                    mm.put("message", "Tạo hợp đồng lỗi!");
                }

            } else {
                mm.put("message", "Không tìm thấy kiểu phòng!");
            }
        }

        return "redirect:/api/v1/room/"+roomId;
    }

    @RequestMapping(value = "deleteContract")
    public String deleteContract(@RequestParam("contractId") int contractId, ModelMap mm, HttpSession session) {

        Contracts contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hợp đồng có id:" + contractId));
        if (contract != null && !contract.getContractStatus()) {
            contractRepository.delete(contract);
            mm.put("message", "Xoá thành công hợp đồng ID: " + contract.getContractId());
        } else {
            mm.put("message", "Hợp đồng đã được xác nhận không thể xoá!");
        }

        int roomId = contract.getRoomId();
        return "redirect:/api/v1/room/"+roomId;
    }

    @RequestMapping(value = "liquidationContract")
    public String liquidationContract(@RequestParam("contractId") int contractId, ModelMap mm, HttpSession session) {

        Contracts contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hợp đồng có id:" + contractId));

        if (contract != null && contract.getContractStatus() && contract.getContractLiquidationTime()==null) {

            java.util.Date date = new java.util.Date();
            Timestamp ts = new Timestamp(date.getTime());

            contract.setContractLiquidationTime(ts);
            contract = contractRepository.save(contract);
            Room room = contract.getRoomByRoomId();
            room.setUserId(null);
            roomRepository.save(room);

            if(contract.getContractLiquidationTime() != null)
                mm.put("message", "Thanh lý thành công hợp đồng : " + contract.getContractId());
        } else {
            mm.put("message", "Hợp đồng đã thanh lý hoặc chưa được xác nhận");
        }

        int roomId = contract.getRoomId();
        return "redirect:/api/v1/room/"+roomId;
    }

    @RequestMapping(value = "getContract/{contractid}")
    public String getUpdateContract(@PathVariable("contractid") int contractid,
                                    ModelMap mm, HttpSession session) {
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

        String getAllContract = getAllContract(mm, session);
        return getAllContract;
    }

    @RequestMapping(value = "viewcontract")
    public String getContractSite(ModelMap mm, HttpSession session) {return getContractBy(mm,session);}

    @RequestMapping(value = "getContractBy")
    public String getContractBy(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "redirect:/api/v1/user/login";
        }
        List<Contracts> contractList = contractRepository.findAll();
        List<Contracts> contractsByIdUser = new ArrayList<>();

        for(Contracts c:contractList ){
            if(c.getUserId() == accSession.getUserId()){
                contractsByIdUser.add(c);
//                c.getDeposit()
            }
        }
        User u = userRepository.findOneByUserId(accSession.getUserId());

        mm.put("Contracts",contractsByIdUser);
        mm.put("user",u);
        return "usercontract";
    }

    @RequestMapping(value = "viewcontractdetail")
    public String getDetailContractSite(ModelMap mm, HttpSession session, @ModelAttribute("contractID") int contractID) {return getDetailContract(contractID,mm,session);}

    @RequestMapping (value = "getDetailContract")
    public String getDetailContract(int contractID, ModelMap mm, HttpSession session){
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "redirect:/api/v1/user/login";
        }
        Optional<Contracts> contract = contractRepository.findById(contractID);

        mm.put("contractdetail",contract.get());
        return "contractuserdetail";
    }





    @PostMapping (value = "confirmContract")
    public String confirmContract(@RequestParam int contractId, RedirectAttributes redirectAttributes, ModelMap mm, HttpSession session){
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");

            return "redirect:/api/v1/user/login";
        }

        Contracts contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hợp đồng!"));

        if (contract.getUserId() == accSession.getUserId()) {
            contract.setContractStatus(true);
            contractRepository.save(contract);

            Room room = contract.getRoomByRoomId();
            room.setUserId(contract.getUserId());
            roomRepository.save(room);
        }

        redirectAttributes.addFlashAttribute("flashAttr","Xác nhận hợp đồng thành công!");
        return "redirect:/api/v1/contract/viewcontract";
    }






}