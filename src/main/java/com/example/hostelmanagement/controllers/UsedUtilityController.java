package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
//    @RequestMapping(value = "dien")
//    public String electricitySite() {
//
//        return "redirect:dien/";
//    }
    @RequestMapping(value = {"{utility-name}", "{utility-name}/search"})
    public String getUtilitySite(@PathVariable("utility-name") String name, ModelMap mm) {
        String utilityName = name;
        List<UsedUtility> utilities = usedUtilityRepository.findAllByUtilityName(utilityName);
        mm.put("utilities",utilities);
        if ("Dien".equalsIgnoreCase(name)) return "electricity";
        return "water";
    }
    @PostMapping(value ="{utility-name}/insert")
    public String insertUltility(@PathVariable("utility-name") String name,
                                 @RequestParam int roomId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 ModelMap mm,
                                 HttpSession session
                                 ) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + roomId));
        Collection<UtilityType> utilityTypes = room.getRoomTypeByTypeId().getHostelByHostelId().getUtilityTypesByHostelId();

        int utilityTypeId = 0;
        double pricePerIndex = 0;
        for (UtilityType utilityType: utilityTypes) {
            if (utilityType.getUtilityName().equalsIgnoreCase(name)) {
                utilityTypeId = utilityType.getUtilityTypeId();
                pricePerIndex = utilityType.getPricePerIndex();
            }
        }
        if (utilityTypeId != 0) {


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
            mm.put("message","Insert success");
        }
        return "redirect:";
    }

    @PostMapping(value ="{utility-name}/update")
    public String updateUltility(@PathVariable("utility-name") String name,
                                 @RequestParam int usedUtilityId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 ModelMap mm,
                                 HttpSession session
    ) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);

        if (usedUtilityOptional.isPresent()) {
            UsedUtility usedUtility = usedUtilityOptional.get();
            usedUtility.setStartDate(startDate);
            usedUtility.setOldIndex(oldIndex);
            usedUtility.setEndDate(endDate);
            usedUtility.setNewIndex(newIndex);

            usedUtilityRepository.save(usedUtility);
            mm.put("message","Insert success");
        }
        return "redirect:";
    }
    @PostMapping(value ="{utility-name}/delete")
    public String deleteUltility(@PathVariable("utility-name") String name,
                                 @RequestParam int usedUtilityId,
                                 ModelMap mm,
                                 HttpSession session
    ) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);
        if (usedUtilityOptional.isPresent()) {
            usedUtilityRepository.delete(usedUtilityOptional.get());
            mm.put("message","delete success");
        }

        return "redirect:";
    }
    @GetMapping(value = {"{utility-name}"})
    public String getElectricity(@PathVariable("utility-name") String name, ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Đăng nhập để tiếp tục");
            return "login";
        }
        int owner = accSession.getUserId();

        String utilityName = name;
        List<UsedUtility> usedUtilities = usedUtilityRepository.findAllByUtilityName(utilityName);
        List<UsedUtility> usedUtilities1 = new ArrayList<>();

        for (UsedUtility usedUtility : usedUtilities ) {
            int _owner = usedUtility.getUtilityTypeByUtilityTypeId().getHostelByHostelId().getOwnerHostelId();
            if (owner == _owner) {
                usedUtilities1.add(usedUtility);
            }
        }

        //utilities.forEach(utility -> utility.setRenterName(usedUtilityRepository.getRenterNameByRoomId(utility.getRoomId(),utility.getUtilityTypeId())));
        mm.put("utilities",usedUtilities1);

        if ("Dien".equalsIgnoreCase(name)) return "electricity";
        if ("nuoc".equalsIgnoreCase(name)) return "water";
        return "error";
    }

}
