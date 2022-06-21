package com.example.hostelmanagement.controllers.Hostel;

import com.example.hostelmanagement.models.Room;
import com.example.hostelmanagement.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/Room")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping(value = "/getAllRoom")
    public String getAllRoomTypes(ModelMap mm) {

        List<Room> listRooms = roomRepository.getAllByRoomStatusTrue();
        mm.put("listRooms", listRooms);

        return "test/roomavaiable";
    }

    @GetMapping(value = "/getRoomAvaiableByTypeId")
    public String getRoomTypesById(@RequestParam("typeid") int typeid, ModelMap mm) {

        List<Room> listRooms = roomRepository.getTblRoomByUserIdNullAndRoomStatusTrueAndTypeId(typeid);
        mm.put("listRooms", listRooms);

        return "test/roomavaiable";
    }



}
