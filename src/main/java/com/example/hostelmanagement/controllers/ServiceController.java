package com.example.hostelmanagement.controllers;


import com.example.hostelmanagement.entities.*;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.ServiceTypeRepository;
import com.example.hostelmanagement.repositories.UsedServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping(value = "api/v1/service/")
public class ServiceController {
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private UsedServiceRepository usedServiceRepository;
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private RoomRepository roomRepository;

    @PostMapping(value ="insert-used-service")
    public String insertUsedService(@RequestParam int roomId,
                                    @RequestParam java.sql.Date startDate,
                                    @RequestParam java.sql.Date endDate,
                                    @RequestParam int servicetypeId,
                                    @RequestParam int usedQuantity,
                                    ModelMap mm) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + roomId));

        double price = 0;

        Collection<ServiceType> serviceTypes = room.getRoomTypeByTypeId().getHostelByHostelId().getServiceTypesByHostelId();

        for (ServiceType serviceType : serviceTypes) {
            price += serviceType.getPrice();
        }

        UsedService usedService = UsedService.builder()
                    .roomId(roomId)
                    .startDate(startDate)
                    .endDate(endDate)
                    .usedQuantity(usedQuantity)
                    .servicetypeId(servicetypeId)
                    .price(price)
                    .build();

        usedServiceRepository.save(usedService);
        mm.put("message","Thêm dịch vụ sử dụng thành công ");

        return "redirect:";
    }


    @PostMapping(value ="update-used-service")
    public String updateUsedService(@RequestParam int usedServiceId,
                                    @RequestParam int roomId,
                                    @RequestParam java.sql.Date startDate,
                                    @RequestParam java.sql.Date endDate,
                                    @RequestParam int usedQuantity,
                                    HttpSession session,
                                    ModelMap mm) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + roomId));
        UsedService usedService = usedServiceRepository.findById(usedServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid used service id: " + usedServiceId));

        if (endDate.before(startDate))  {
            mm.put("message","Ngày kết thúc không được nhỏ ngày bắt đầu.");
            return getAllServicesType(mm, session);
        }
        usedService.setStartDate(startDate);
        usedService.setEndDate(endDate);

        if (usedQuantity < 0)  {
            mm.put("message","Số lượng sử dụng không được nhỏ hơn 0.");
            return getAllServicesType(mm, session);
        }

        usedService.setUsedQuantity(usedQuantity);

        usedServiceRepository.save(usedService);
        mm.put("message","Cập nhật dịch vụ sử dụng thành công ");

        return getAllServicesType(mm, session);
    }

    @PostMapping(value = "delete-service")
    public String deleteService(HttpSession session,
                                @RequestParam int usedServiceID,
                                ModelMap mm) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }

        UsedService usedService = usedServiceRepository.findById(usedServiceID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Used Service Id:" + usedServiceID));

        usedServiceRepository.delete(usedService);

        mm.put("message","Xóa thành công dịch vụ sử dụng với id " + usedServiceID + " thành công!!!");

        return getAllServicesType(mm, session);
    }





    @PostMapping(value = "update-service-type")
    public String updateServiceType(HttpSession session,
                                    @RequestParam int serviceTypeId,
                                    @RequestParam String serviceName,
                                    @RequestParam double price,
                                    ModelMap mm) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service Type Id:" + serviceTypeId));
        serviceType.setServiceName(serviceName);
        serviceType.setPrice(price);
        serviceTypeRepository.save(serviceType);
        mm.put("message","Update service type successfully");

        return getAllServicesType(mm, session);
    }

    @PostMapping(value = "delete-service-type")
    public String deleteServiceType(HttpSession session,
                                    @RequestParam int serviceTypeId,
                                    ModelMap mm) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "/api/v1/user/login";
        }

        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service Type Id:" + serviceTypeId));

        serviceTypeRepository.delete(serviceType);

        mm.put("message","Delete service type with id " + serviceTypeId + " successfully!!!");

        return getAllServicesType(mm, session);
    }

    @PostMapping(value = "insert-type")
    public String insertType(
            @RequestParam int hostelId,
            @RequestParam String serviceName,
            @RequestParam double price,
            HttpSession session,
            ModelMap mm) {

        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            return "/api/v1/user/login";
        }

        ServiceType serviceType = ServiceType.builder()
                .hostelId(hostelId)
                .serviceName(serviceName)
                .price(price)
                .build();
        serviceTypeRepository.save(serviceType);

        mm.put("messages","messages");
        return getAllServicesType(mm, session);
    }

    @GetMapping(value = "/")
    public String getAllServicesType(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            return "/api/v1/user/login";
        }

        Collection<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(accSession.getUserId());
        Collection<ServiceType> serviceTypes = new ArrayList<>();
        hostels.forEach(hostel -> serviceTypes.addAll(hostel.getServiceTypesByHostelId()));

        Collection<UsedService> usedServices = new ArrayList<>();

        hostels.forEach(hostel -> hostel.getServiceTypesByHostelId().forEach(serviceType -> usedServices.addAll(serviceType.getUsedServicesByServiceTypeId())));


        mm.put("usedServices",usedServices);
        mm.put("services",serviceTypes);
        return "services";
    }


}