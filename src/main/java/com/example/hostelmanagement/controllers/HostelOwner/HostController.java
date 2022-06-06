package com.example.hostelmanagement.controllers.HostelOwner;

import com.example.hostelmanagement.models.Hostel;
import com.example.hostelmanagement.models.UsedService;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/Host")
public class HostController {
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsedServiceRepository usedServiceRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomChargeRepository roomChargeRepository;




}
