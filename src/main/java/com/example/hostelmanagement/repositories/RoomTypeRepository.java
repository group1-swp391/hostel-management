package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM tbl_RoomType AS rt WHERE rt.roomName LIKE %:roomName% AND rt.roomTypeStatus=1")
    List<RoomType> findAllByRoomName(@Param("roomName") String roomName);
}
