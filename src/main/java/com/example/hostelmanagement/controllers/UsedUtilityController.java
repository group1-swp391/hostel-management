package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "api/v1/used-utility")
public class UsedUtilityController {

    @Autowired
    private UsedUtilityRepository usedUtilityRepository;

//    @RequestMapping(value = "dien")
//    public String electricitySite() {
//
//        return "redirect:dien/";
//    }
//    @GetMapping(value = {"{utility-name}", "{utility-name}/search"})
//    public String getElectricity(@PathVariable("utility-name") String name, @RequestParam(value = "roomId", required = false) String roomId, ModelMap mm) {
//        String utilityName = name;
//        List<UsedUtility> utilities = usedUtilityRepository.findAllByUtilityName(utilityName);
//        if (roomId!=null&&!"".equals(roomId)) {
//            utilities = usedUtilityRepository.findAllByRoomId(utilityName, Integer.parseInt(roomId));
//        }
//        utilities.forEach(utility -> utility.setRenterName(usedUtilityRepository.getRenterNameByRoomId(utility.getRoomId(),utility.getUtilityTypeId())));
//        mm.put("utilities",utilities);
//        if ("Dien".equalsIgnoreCase(name)) return "electricity";
//        return "water";
//    }
    @GetMapping(value = {"{utility-name}"})
    public String getElectricity(@PathVariable("utility-name") String name, ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            mm.put("message", "Need login first");
            return "error";
        }
        int owner = accSession.getUserId();

        String utilityName = name;
        List<UsedUtility> usedUtilities = usedUtilityRepository.findAllByUtilityName(utilityName);
        List<UsedUtility> usedUtilities1 = usedUtilityRepository.findAllByUtilityName(utilityName);

        for (UsedUtility usedUtility : usedUtilities ) {
            int _owner = usedUtility.getUtilityTypeByUtilityTypeId().getHostelByHostelId().getOwnerHostelId();
            if (owner == _owner) {
                usedUtilities1.add(usedUtility);
            }
        }

        //utilities.forEach(utility -> utility.setRenterName(usedUtilityRepository.getRenterNameByRoomId(utility.getRoomId(),utility.getUtilityTypeId())));
        mm.put("utilities",usedUtilities1);

        if ("Dien".equalsIgnoreCase(name)) return "electricity";
        return "water";
    }
}
