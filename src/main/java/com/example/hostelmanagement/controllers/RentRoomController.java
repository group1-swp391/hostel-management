package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
                              @RequestParam(defaultValue = "1", required = false) Optional<Integer> page,
                              @RequestParam(defaultValue = "", required = false) Optional<String> filterAddress,
                              @RequestParam(required = false) Optional <Integer> filterPrice1,
                              @RequestParam(required = false) Optional <Integer> filterPrice2
    ) {

        if (page.isPresent()) {
            int paramPage = page.get() - 1;
            Pageable pageable =  PageRequest.of(paramPage, 9);
            //int currentPage = pageable.getPageNumber();

            if(filterAddress.isPresent()) {
                String filterAdd = filterAddress.get();

                if(filterPrice1.isPresent() && filterPrice2.isPresent()) {
                    Pageable pageable1 =  PageRequest.of(paramPage, 9, Sort.by("roomId"));

                    List<Room> rooms1 = roomRepository.findAllByRoomStatus(true);
                    List<Room> rooms2 = new ArrayList<>();

                    for (Room item: rooms1) {
                        if (item.getRoomTypeByTypeId().getHostelByHostelId().getAddress().contains(filterAdd) && item.getRoomTypeByTypeId().getPrice() >= filterPrice1.get() && item.getRoomTypeByTypeId().getPrice() <= filterPrice2.get()) {
                            rooms2.add(item);
                        }
                    }
                    //  rooms2.sort(s -> s.getRoomTypeByTypeId().getPrice());

                    int start = Math.min((int)pageable1.getOffset(), rooms2.size());
                    int end = Math.min((start + pageable1.getPageSize()), rooms2.size());

                    Page<Room> rooms3 = new PageImpl<>(rooms2.subList(start, end), pageable1, rooms2.size());

                    mm.put("totalItems", rooms2.size());
                    mm.put("rooms", rooms3);
                    mm.put("totalPage", rooms3.getTotalPages());
                } else {
                    Pageable pageable1 =  PageRequest.of(paramPage, 9, Sort.by("roomId"));

                    List<Room> rooms1 = roomRepository.findAllByRoomStatus(true);
                    List<Room> rooms2 = new ArrayList<>();

                    for (Room item: rooms1) {
                        if (item.getRoomTypeByTypeId().getHostelByHostelId().getAddress().contains(filterAdd)) {
                            rooms2.add(item);
                        }
                    }
                    //  rooms2.sort(s -> s.getRoomTypeByTypeId().getPrice());

                    int start = Math.min((int)pageable1.getOffset(), rooms2.size());
                    int end = Math.min((start + pageable1.getPageSize()), rooms2.size());

                    Page<Room> rooms3 = new PageImpl<>(rooms2.subList(start, end), pageable1, rooms2.size());

                    mm.put("totalItems", rooms2.size());
                    mm.put("rooms", rooms3);
                    mm.put("totalPage", rooms3.getTotalPages());
                }
            }

            mm.put("page", paramPage + 1);
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
