package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/utility-type")
public class UtilityTypeController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @PostMapping(value = "/update")
    public String updateUltilityType(@RequestParam int utilityTypeId,
                                 @RequestParam double pricePerIndex,
                                 ModelMap mm,
                                 RedirectAttributes redirectAttributes) {

        UtilityType utilityType = utilityTypeRepository.findById(utilityTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiện ích có id:" + utilityTypeId));

        utilityType.setPricePerIndex(pricePerIndex);

        utilityTypeRepository.save(utilityType);

        int hostelId = utilityType.getHostelId();

        redirectAttributes.addFlashAttribute("flashAttr","Cập nhật tiện ích thành công!");
        return "redirect:/api/v1/service/hostel/"+hostelId;
    }


    @RequestMapping(value = "/test")
    public String test(ModelMap mm) {
        String toSplit = hostelRepository.findById(1).get().getAddress();
        String result[] = toSplit.split(",");

        String returnValue1 = result[result.length - 1];
        String returnValue2 = result[result.length - 2];
        String returnValue3 = result[result.length - 3];
        String returnValue4 = result[result.length - 4];

        System.out.println(toSplit);

        mm.put("message", toSplit);
        mm.put("message1", returnValue1);
        mm.put("message2", returnValue2);
        mm.put("message3", returnValue3);
        mm.put("message4", returnValue4);

        return "test5";
    }




}
