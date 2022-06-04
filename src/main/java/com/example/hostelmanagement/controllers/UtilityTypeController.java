package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "api/v1/UtilityType/")
public class UtilityTypeController {
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_utilityTypeMngt";
    }

    @PostMapping(value = "insert")
    public String insertUtilityType(ModelMap mm, @RequestParam("newUtilityName") String newUtilityName, @RequestParam("newPricePerIndex") double newPricePerIndex, @RequestParam("newHostelId") int newHostelId) {
        try {
            utilityTypeRepository.save(new UtilityType(newUtilityName, newPricePerIndex, newHostelId));
            mm.put("message", "Insert new hostel successfully!");
        } catch (Exception e) {
            mm.put("message", "Insert new hostel failed!");
        }
        return "host_utilityTypeMngt";
    }

    @GetMapping(value = "delete")
    public String deleteUtilityType(ModelMap mm, @RequestParam(value = "utilityTypeId") int utilityTypeId) {
        try {
            UtilityType utilityType = utilityTypeRepository.findById(utilityTypeId).get();
            utilityTypeRepository.delete(utilityType);
            mm.put("message","Delete utility type successfully");
        } catch (Exception e) {
            mm.put("message", "Delete utility type failed");
        } finally {
            return "host_utilityTypeMngt";
        }
    }
    @GetMapping(value = "update")
    public String updateUtilityType(ModelMap mm, @RequestParam(value = "utilityTypeId") int utilityTypeId, @RequestParam("utilityName") String utilityName, @RequestParam("pricePerIndex") double pricePerIndex, @RequestParam("hostelId") int hostelId) {
        try {
            UtilityType utilityType = utilityTypeRepository.findById(utilityTypeId).get();
            utilityType.setUtilityName(utilityName);
            utilityType.setPricePerIndex(pricePerIndex);
            utilityType.setHostelId(hostelId);
            utilityTypeRepository.save(utilityType);
            mm.put("message","Update utility type successfully");
        } catch (Exception e) {
            mm.put("message", "Update utility type failed");
        } finally {
            return "host_utilityTypeMngt";
        }
    }

    @GetMapping(value = "search")
    public String getAllUtilityType(@RequestParam(value = "utilityName", required = false) String utilityName, ModelMap mm) {
        mm.put("utilityName", utilityName);
        mm.put("utilityTypes", utilityTypeRepository.findAllByUtilityNameContains(utilityName));
        return "host_utilityTypeMngt";
    }
}