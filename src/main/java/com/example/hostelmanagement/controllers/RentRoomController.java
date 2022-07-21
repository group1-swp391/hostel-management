package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/rentroom")
public class RentRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

//    @GetMapping(value = {"/","search"})
//    public String getAllRooms(@RequestParam(value = "roomNumber", required = false) String roomNumber, ModelMap mm) {
//        mm.put("roomNumber", roomNumber);
//        List<Room> rooms = roomRepository.findAllByRoomStatus(true,);
//        if (roomNumber!=null && !"".equals(roomNumber.trim())) {
//            rooms = roomRepository.findAllByRoomNumberAndRoomStatus(Integer.parseInt(roomNumber), true);
//        }
//        rooms.forEach(room -> Utils.putPriceAndTypeNameToRoom(roomTypeRepository, room));
//        mm.put("rooms", rooms);
//
//        return "room";
//    }

    @GetMapping(value = {"list-room"})
    public String getAllRooms(ModelMap mm,
                              @RequestParam(defaultValue = "1", required = false) Optional<Integer> page) {

        if (page.isPresent()) {
            Pageable pageable =  PageRequest.of(page.get(), 5);
            int currentPage = pageable.getPageNumber();

            List<Room> rooms = roomRepository.findAllByRoomStatus(true, pageable);
            List<Room> rooms1 = roomRepository.findAllByRoomStatus(true);

            int totalElements = rooms1.size();

            int totalPage = (totalElements + pageable.getPageSize() - 1)/pageable.getPageSize();

            mm.put("rooms", rooms);
            mm.put("currentPage", currentPage);
            mm.put("totalPage", totalPage);
        }
        return "room";
    }

    @GetMapping(value = "/{id}/info-room")
    public String infoRoom(@PathVariable("id") int id, ModelMap mm) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));;
        if (room != null) {
            mm.put("room", room);
        }
        return "detail";
    }


}
