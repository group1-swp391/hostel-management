package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping(value = {"api/v1/hostel","api/v1/host"})
public class HostelContoller {
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;

    @RequestMapping(value = "")
    public String getAllHostel(  @RequestParam(required = false) Optional<Boolean> updateHostel,
                                 @RequestParam(required = false) Optional<Integer> updateHostelId,
                                 ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute("LOGIN_USER");

        if (user == null) {
            return "redirect:/api/v1/user/login";
        }

        List<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(user.getUserId());
        hostels.forEach(hostel -> {
            AtomicInteger roomNumber = new AtomicInteger();
            hostel.getRoomTypesByHostelId().forEach(roomType -> roomNumber.addAndGet(roomType.getRoomsByTypeId().size()));
            hostel.setRoomNumber(roomNumber.get());
        });
        mm.addAttribute("hostelObj", new Hostel());

        if (updateHostel.isPresent() && updateHostelId.isPresent()) {
            if (updateHostel.get()) {
                Hostel hostel = hostelRepository.findById(updateHostelId.get())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiện ích sử dụng"));
                mm.put("hostel", hostel);
            }
        }

        mm.put("hostels", hostels);

        return "hostpage";
    }

    @PostMapping(value = "insert")
    public String createHostel(Hostel hostelObj,
                               @RequestParam double electricityValue,
                               @RequestParam double waterValue,
                               ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute("LOGIN_USER");
        if (user == null) {
            return "redirect:/api/v1/user/login";
        }
        hostelObj.setOwnerHostelId(user.getUserId());
        hostelObj.setHostelStatus(true);
        hostelRepository.save(hostelObj);
        utilityTypeRepository.saveAll(List.of(
            UtilityType.builder().utilityName("Dien").hostelId(hostelObj.getHostelId()).pricePerIndex(electricityValue).build(),
            UtilityType.builder().utilityName("Nuoc").hostelId(hostelObj.getHostelId()).pricePerIndex(waterValue).build()
                ));
        mm.put("message",  "Thêm nhà trọ thành công");
        return "redirect:";
    }

    @PostMapping(value = "update")
    public String updateHostel(@RequestParam int hostelId,
                               @RequestParam String address,
                               @RequestParam String hostelName,
                               ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute("LOGIN_USER");
        if (user == null) {
            return "redirect:/api/v1/user/login";
        }
        Hostel hostel = hostelRepository.findById(hostelId).get();
        hostel.setHostelName(hostelName);
        hostel.setAddress(address);
        hostelRepository.save(hostel);
        mm.put("message","Cập nhật nhà trọ thành công");
        return "redirect:";
    }

    @RequestMapping(value = "delete")
    public String deleteHostel(@RequestParam int hostelId, ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute("LOGIN_USER");
        if (user == null) {
            return "redirect:/api/v1/user/login";
        }
        Hostel hostel = hostelRepository.findById(hostelId).get();
        hostel.setHostelStatus(false);
        hostelRepository.save(hostel);
        mm.put("message", "Xóa nhà trọ thành công");
        return "redirect:";
    }

}