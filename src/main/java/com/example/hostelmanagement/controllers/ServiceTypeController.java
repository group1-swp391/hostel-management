package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.ServiceType;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.ServiceTypeRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "api/v1/ServiceType/")
public class ServiceTypeController {
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @GetMapping(value = "/")
    public String hostIndex() {
        return "host_serviceTypeMngt";
    }

    @PostMapping(value = "insert")
    public String insertUtilityType(ModelMap mm, @RequestParam("newServiceName") String newServiceName, @RequestParam("newPrice") double newPrice, @RequestParam("newHostelId") int newHostelId) {
        try {
            serviceTypeRepository.save(new ServiceType(newServiceName, newPrice, newHostelId));
            mm.put("message", "Insert new service type successfully!");
        } catch (Exception e) {
            mm.put("message", "Insert new service failed!");
        }
        return "host_serviceTypeMngt";
    }

    @GetMapping(value = "delete")
    public String deleteUtilityType(ModelMap mm, @RequestParam(value = "serviceTypeId") int serviceTypeId) {
        try {
            ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId).get();
            serviceTypeRepository.delete(serviceType);
            mm.put("message","Delete service type successfully");
        } catch (Exception e) {
            mm.put("message", "Delete service type failed");
        } finally {
            return "host_serviceTypeMngt";
        }
    }
    @GetMapping(value = "update")
    public String updateUtilityType(ModelMap mm, @RequestParam(value = "serviceTypeId") int serviceTypeId, @RequestParam("serviceName") String serviceName, @RequestParam("price") double price, @RequestParam("hostelId") int hostelId) {
        try {
            ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId).get();
            serviceType.setServiceName(serviceName);
            serviceType.setPrice(price);
            serviceType.setHostelId(hostelId);
            serviceTypeRepository.save(serviceType);
            mm.put("message","Update service type successfully");
        } catch (Exception e) {
            mm.put("message", "Update service type failed");
        } finally {
            return "host_serviceTypeMngt";
        }
    }

    @GetMapping(value = "search")
    public String getAllServiceType(@RequestParam(value = "serviceName", required = false) String serviceName, ModelMap mm) {
        mm.put("serviceName", serviceName);
        mm.put("serviceTypes", serviceTypeRepository.findAllByServiceNameContains(serviceName));
        return "host_serviceTypeMngt";
    }
}