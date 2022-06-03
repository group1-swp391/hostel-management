package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Room r WHERE r.roomNumber = :roomNumber AND r.roomStatus=1")
    List<Room> findAllByRoomNumber(@Param("roomNumber") int roomNumber);
}
