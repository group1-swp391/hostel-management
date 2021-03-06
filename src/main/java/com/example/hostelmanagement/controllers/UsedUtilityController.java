package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/used-utility")
public class UsedUtilityController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @PostMapping(value ="/insert")
    public String insertUltility(@RequestParam int utilityTypeId,
                                 @RequestParam int roomId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 ModelMap mm,
                                 RedirectAttributes redirectAttributes
                                 )
    {
        try {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new IllegalArgumentException("Kh??ng t??m th???y ph??ng c?? id: " + roomId));
            Collection<UtilityType> utilityTypes = room.getRoomTypeByTypeId().getHostelByHostelId().getUtilityTypesByHostelId();

            UtilityType utilityType = utilityTypeRepository.findById(utilityTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Kh??ng t??m th???y ti???n ??ch c?? id: " + utilityTypeId));

            double pricePerIndex = utilityType.getPricePerIndex();

            UsedUtility usedUtility = UsedUtility.builder()
                                                .roomId(roomId)
                                                .startDate(startDate)
                                                .endDate(endDate)
                                                .oldIndex(oldIndex)
                                                .utilityTypeId(utilityTypeId)
                                                .newIndex(newIndex)
                                                .pricePerIndex(pricePerIndex)
                                                .build();
            usedUtilityRepository.save(usedUtility);

            //mm.put("message", "Th??m ph??ng s??? d???ng ti???n ??ch " + utilityType.getUtilityName() + " th??nh c??ng");

            redirectAttributes.addFlashAttribute("flashAttr","Th??m ph??ng s??? d???ng " +  utilityType.getUtilityName() + " th??nh c??ng");
        }
        catch (Exception ex) {
            mm.put("message", "L???i th??m d???ch v??? s??? d???ng" + ex.getMessage());
            return "error";
        }
        return "redirect:/api/v1/room/"+roomId;
    }

    @PostMapping(value ="/update")
    public String updateUltility(@RequestParam int usedUtilityId,
                                 @RequestParam int utilityTypeId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 @RequestParam double pricePerIndex,
                                 ModelMap mm,  RedirectAttributes redirectAttributes) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);

        if (usedUtilityOptional.isPresent()) {
            //Validate and authorzation...
            UsedUtility usedUtility = usedUtilityOptional.get();
            usedUtility.setStartDate(startDate);
            usedUtility.setOldIndex(oldIndex);
            usedUtility.setEndDate(endDate);
            usedUtility.setNewIndex(newIndex);
            usedUtility.setUtilityTypeId(utilityTypeId);
            usedUtility.setPricePerIndex(pricePerIndex);
            usedUtilityRepository.save(usedUtility);

            redirectAttributes.addFlashAttribute("flashAttr","C???p nh???t ti???n ??ch s??? d???ng " + usedUtilityOptional.get().getUtilityTypeByUtilityTypeId().getUtilityName() + " th??nh c??ng!");
            int roomId = usedUtilityOptional.get().getRoomId();
            return "redirect:/api/v1/room/"+roomId;
        } else {
            mm.put("message","Kh??ng t??m th???y ti???n ??ch s??? d???ng");
            return "error";
        }

    }

    @PostMapping(value ="/delete")
    public String deleteUltility(@RequestParam int usedUtilityId,
                                 ModelMap mm, RedirectAttributes redirectAttributes) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);
        if (usedUtilityOptional.isPresent()) {
            usedUtilityRepository.delete(usedUtilityOptional.get());

            redirectAttributes.addFlashAttribute("flashAttr","X??a ti???n ??ch s??? d???ng th??nh c??ng");

            return "redirect:/api/v1/room/"+usedUtilityOptional.get().getRoomId();
        } else {
            mm.put("message","Kh??ng t??m th???y ti???n ??ch s??? d???ng");
            return "error";
        }
    }
}
