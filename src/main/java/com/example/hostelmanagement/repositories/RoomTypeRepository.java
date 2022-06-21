package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    List<RoomType> findAllByRoomNameContainsAndRoomTypeStatus(String roomName, boolean roomTypeStatus);

    List<RoomType> getTblRoomTypeByRoomTypeStatusTrue();
    List<RoomType> getTblRoomTypeByRoomTypeStatusTrueAndHostelId(int hostelId);

    RoomType getRoomTypeByTypeId(int roomTypeId);
}
