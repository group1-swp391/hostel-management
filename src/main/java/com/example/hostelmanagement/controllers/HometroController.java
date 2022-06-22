package com.example.hostelmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping(value = "info")
    public String roomInfo() {
        return "inforoom";
    }

    @RequestMapping(value = "invoice")
    public String historySite() { return "redirect:/api/v1/invoice/"; }

    @RequestMapping(value = "roomcharge")
    public String getRoomChargeSite() { return "redirect:/api/v1/roomcharge/"; }

    @RequestMapping(value = "electricity")
    public String electricitySite() { return "redirect:/api/v1/used-utility/dien"; }

    @RequestMapping(value = "water")
    public String waterSite() { return "redirect:/api/v1/used-utility/nuoc"; }
}
