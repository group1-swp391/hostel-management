package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/hometro")
public class HometroController {



    @RequestMapping(value = "user")
    public String renterSite() {
        return "user";
    }

    @RequestMapping(value = "to-rentroom")
    public String rentRoomSite() {
        return "redirect:/api/v1/rentroom/";
    }

    @RequestMapping(value = "money")
    public String moneySite() { return "money"; }

    @RequestMapping(value = "service")
    public String serviceSite() { return "services"; }

    @RequestMapping(value = "contract")
    public String contractSite() { return "contract"; }

    @RequestMapping(value = "history")
    public String historySite() { return "history"; }

    @RequestMapping(value = "electricity")
    public String electricitySite() { return "electricity"; }

    @RequestMapping(value = "water")
    public String waterSite() { return "water"; }
}
