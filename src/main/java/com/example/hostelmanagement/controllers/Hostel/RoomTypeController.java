package com.example.hostelmanagement.controllers.Hostel;

import com.example.hostelmanagement.models.RoomType;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "api/v1/RoomType")
public class RoomTypeController {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @RequestMapping(value = "/getAllRoomType")
    public String getAllRoomTypes(ModelMap mm) {

        List<RoomType> listRoomTypes = roomTypeRepository.getTblRoomTypeByRoomTypeStatusTrue();
        mm.put("listRoomTypes", listRoomTypes);

        return "test/listroomtype";
    }

    @GetMapping(value = "/getRoomTypeByHostelId")
    public String getRoomTypesById(@RequestParam("hostelid") int hostelId, ModelMap mm) {

        List<RoomType> listRoomTypes = roomTypeRepository.getTblRoomTypeByRoomTypeStatusTrueAndHostelId(hostelId);
        mm.put("listRoomTypes", listRoomTypes);

        return "test/listroomtype";
    }

    @RequestMapping(value = "/insertRoomType")
    public String insertRoomType() {
        RoomType n1 = new RoomType(1,"1",500.00,500.00,"ok",true,null);
        roomTypeRepository.save(n1);

        return "test/listroomtype";
    }



}
