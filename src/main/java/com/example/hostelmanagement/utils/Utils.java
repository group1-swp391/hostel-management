package com.example.hostelmanagement.utils;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.RoomType;
import com.example.hostelmanagement.repositories.RoomTypeRepository;

import javax.servlet.http.Part;
import java.io.IOException;

public class Utils {
    public static byte[] getByteImage(Part image) throws IOException {
        return image.getInputStream().readAllBytes();
    }
    public static void putPriceAndTypeNameToRoom(RoomTypeRepository roomTypeRepository, Room room) {
        RoomType roomType = roomTypeRepository.findById(room.getTypeId()).get();
        room.setPrice(roomType.getPrice());
        room.setRoomName(roomType.getRoomName());
    }
}
