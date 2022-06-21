package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByRoomNumberAndRoomStatus(int roomNumber, boolean roomStatus);
    List<Room> findAllByRoomStatus(boolean roomStatus);
    @Query(nativeQuery = true, value = "SELECT userName FROM tbl_Users WHERE userID=:userId")
    String findUserNameByUserId(@Param("userId") int userId);


    List<Room> getTblRoomByUserIdNullAndRoomStatusTrueAndTypeId(int typeId);
    List<Room> getAllByRoomStatusTrue();

    Room getRoomByRoomId(int roomId);
}
