package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping(value = {"{utility-name}", "{utility-name}/search"})
    public String getElectricity(@PathVariable("utility-name") String name, @RequestParam(value = "roomId", required = false) String roomId, ModelMap mm) {
        String utilityName = "Dien";
        List<UsedUtility> utilities = usedUtilityRepository.findAllByUtilityName(utilityName);
        if (roomId!=null&&!"".equals(roomId)) {
            utilities = usedUtilityRepository.findAllByRoomId(utilityName, Integer.parseInt(roomId));
        }
        utilities.forEach(utility -> utility.setRenterName(usedUtilityRepository.getRenterNameByRoomId(utility.getRoomId(),utility.getUtilityTypeId())));
        mm.put("utilities",utilities);
        if ("Dien".equalsIgnoreCase(name)) return "electricity";
        return "water";
    }
}
