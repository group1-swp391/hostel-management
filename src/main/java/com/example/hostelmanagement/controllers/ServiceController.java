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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                    ModelMap mm,
                                    RedirectAttributes redirectAttributes) {

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
        //mm.put("message","Thêm dịch vụ sử dụng thành công ");
        redirectAttributes.addFlashAttribute("flashAttr","Thêm dịch vụ sử dụng thành công");
        return "redirect:/api/v1/room/"+roomId;
    }

    @RequestMapping (value ="{usedServiceId}")
    public String getUsedService(@PathVariable int usedServiceId,
                                 ModelMap mm,
                                 HttpSession session) {

        UsedService usedService = usedServiceRepository.findById(usedServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Id không hợp lệ :" + usedServiceId));

        mm.put("usedService",usedService);

        return getAllServicesType(mm, session);
    }

    @PostMapping(value ="update-used-service")
    public String updateUsedService(@RequestParam int usedServiceId,
                                    @RequestParam java.sql.Date startDate,
                                    @RequestParam java.sql.Date endDate,
                                    @RequestParam double price,
                                    @RequestParam int servicetypeId,
                                    @RequestParam int usedQuantity,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes,
                                    ModelMap mm) {

        UsedService usedService = usedServiceRepository.findById(usedServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid used service id: " + usedServiceId));
        int roomId = usedService.getRoomId();

        usedService.setServicetypeId(servicetypeId);

        if (endDate.before(startDate))  {
            redirectAttributes.addFlashAttribute("flashAttr","Ngày kết thúc phải sau ngày bắt đầu!");
            return "redirect:/api/v1/room/"+roomId;
        }
        usedService.setStartDate(startDate);
        usedService.setEndDate(endDate);
        if (usedQuantity < 0)  {
            redirectAttributes.addFlashAttribute("flashAttr","Số lượng sử dụng không được nhỏ hơn 0!");
            return "redirect:/api/v1/room/"+roomId;
        }
        usedService.setUsedQuantity(usedQuantity);
        if (price <= 0)  {
            redirectAttributes.addFlashAttribute("flashAttr","Giá phải lớn hơn 0!");
            return "redirect:/api/v1/room/"+roomId;
        }
        usedService.setPrice(price);

        usedServiceRepository.save(usedService);

        mm.put("message","Cập nhật dịch vụ sử dụng thành công!");
        redirectAttributes.addFlashAttribute("flashAttr","Cập nhật dịch vụ sử dụng thành công!");

        return "redirect:/api/v1/room/"+roomId;
    }

    @PostMapping(value = "delete-service")
    public String deleteService(HttpSession session,
                                @RequestParam int usedServiceId,
                                ModelMap mm,  RedirectAttributes redirectAttributes) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "redirect:/api/v1/user/login";
        }

        UsedService usedService = usedServiceRepository.findById(usedServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Used Service Id:" + usedServiceId));

        usedServiceRepository.delete(usedService);
        int roomId = usedService.getRoomId();

        redirectAttributes.addFlashAttribute("flashAttr","Xóa thành công dịch vụ sử dụng với id " + usedServiceId + " thành công!!!");
        return "redirect:/api/v1/room/"+roomId;

    }

    @PostMapping(value = "update-service-type")
    public String updateServiceType(HttpSession session,
                                    @RequestParam int serviceTypeId,
                                    @RequestParam String serviceName,
                                    @RequestParam double price,
                                    ModelMap mm) {
        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "redirect:/api/v1/user/login";
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service Type Id:" + serviceTypeId));

        int hostelId = serviceType.getHostelId();

        serviceType.setServiceName(serviceName);
        serviceType.setPrice(price);
        serviceTypeRepository.save(serviceType);

        mm.put("messages","Cập nhật dịch vụ thành công");

        return getAllServicesTypeByHostelId(hostelId, mm, session);
    }

    @PostMapping(value = "delete-service-type")
    public String deleteServiceType(HttpSession session,
                                    @RequestParam int serviceTypeId,
                                    ModelMap mm) {

        User accSession = (User) session.getAttribute("LOGIN_USER");
        if (accSession == null) {
            return "redirect:/api/v1/user/login";
        }

        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Service Type Id:" + serviceTypeId));
        int hostelId = serviceType.getHostelId();
        serviceTypeRepository.delete(serviceType);

        mm.put("messages","Xoá thành công dịch vụ " + serviceTypeId);

        return getAllServicesTypeByHostelId(hostelId, mm, session);
    }

    @PostMapping(value = "insert-type")
    public String insertType(@RequestParam int hostelId,
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

        mm.put("messages","Thêm thành công dịch vụ " + serviceType.getServiceTypeId());
        return getAllServicesTypeByHostelId(hostelId, mm, session);
    }


    @GetMapping(value = "")
    public String getAllServicesType(ModelMap mm, HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            return "redirect:/api/v1/user/login";
        }

        Collection<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(accSession.getUserId());

        mm.put("hostels",hostels);
        if (hostels != null) {
            if (hostels.stream().findFirst().isPresent()) {
                return "redirect:/api/v1/service/hostel/" + hostels.stream().findFirst().get().getHostelId();
            }
        }

        return "services";
    }

    @GetMapping(value = "/hostel/{hostelId}")
    public String getAllServicesTypeByHostelId(@PathVariable int hostelId,
                                               ModelMap mm,
                                               HttpSession session) {
        User accSession = (User) session.getAttribute("LOGIN_USER");

        if (accSession == null) {
            return "redirect:/api/v1/user/login";
        }

        Collection<Hostel> hostels = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(accSession.getUserId());
        Collection<ServiceType> serviceTypes = new ArrayList<>();
        Collection<UtilityType> utilityTypes = new ArrayList<>();

        Hostel hostel = hostelRepository.findById(hostelId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhà trọ có id:" + hostelId));
        serviceTypes = hostel.getServiceTypesByHostelId();
        utilityTypes = hostel.getUtilityTypesByHostelId();

        mm.put("hostel",hostel);
        mm.put("hostels",hostels);
        mm.put("serviceTypes",serviceTypes);
        mm.put("utilityTypes",utilityTypes);

        return "services";
    }

}